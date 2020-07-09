package wordCounter;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordCounter {

	private static final Logger LOG = LogManager.getLogger();

	// given the file location, text is parsed into a single String with each line
	// appended with a space
	public static String parseText(String fileLocation) throws IOException {
		BufferedReader txtReader = null;
		try {
			txtReader = new BufferedReader(new FileReader(fileLocation));
		} catch (Exception e) {
			LOG.error("File not found,  please check file location ");
			return null;
		}
		// to append each word on a single result object(StringBuilder)
		StringBuilder result = new StringBuilder();
		String line = txtReader.readLine();
		while (line != null) {
			// end of line reached add a space for 'StringTokenizer' to count words
			result.append(line).append(" ");
			line = txtReader.readLine();
		}
		txtReader.close();
		return result.toString();
	}

	// total words
	public static int getWordCount(String words) {
		if (words == null || words.isEmpty()) {
			return 0;
		}
		StringTokenizer tokens = new StringTokenizer(words); //
		return tokens.countTokens();
	}

	// frequency of words of total text
	public static Map<Integer, Integer> getWordFrequencies(String text) {
		// KEY : length of a word
		// VALUE : frequency of that word
		Map<Integer, Integer> words = new HashMap<>(); // KEY + VALUE pairs

		// get each word one by one using the 'StringTokenizer'
		StringTokenizer tokens = new StringTokenizer(text);
		while (tokens.hasMoreTokens()) {
			// filter/remove the full stops just at the end of a word
			String token = deleteFullStops(tokens.nextToken());
			int length = token.length();

			// put or update that key. If a word of that length does not exist 'put' 1 else
			// update frequency by +1
			if (!words.containsKey(length)) {
				words.put(length, 1);
			} else
				words.put(length, words.get(length) + 1);
		}
		return words;
	}

	// words attached to a "." , such as 'morning.' transformed into 'morning'
	public static String deleteFullStops(String str) {
		String result = Optional.ofNullable(str).filter(sStr -> sStr.endsWith("."))
				.map(sStr -> sStr.substring(0, sStr.length() - 1)).orElse(str);
		return result;
	}

	public static double getAverageWordLength(String text) {
		Map<Integer, Integer> words = getWordFrequencies(text);
		double sumOfWordLengths = words.entrySet().stream().mapToDouble((w) -> w.getKey() * w.getValue()).sum();
		double sumOfWordFrequecies = words.entrySet().stream().mapToDouble((w) -> w.getValue()).sum();
		return sumOfWordLengths / sumOfWordFrequecies;
	}

	public static void displayListOfWordFrequencies(String text) {
		Map<Integer, Integer> words = getWordFrequencies(text);
		words.forEach((k, v) -> System.out.println("Number of words of length " + k + " is " + v));
	}

	public static void displayMostFrequentWord(String text) {
		// main map 'words', has all occurence key value pairs
		Map<Integer, Integer> words = getWordFrequencies(text);
		// map of 'words' grouped by frequency =>
		// such as if length 2 has 2 elements and length 3 has 2 elements and length 5
		// has 2 elements {2=2, 3=2, 5=2}
		// than create map with key=2(length of word) and value=3(number of occurances)
		Map<Integer, Long> groupByFrequency = words.values().stream()
				.collect(Collectors.groupingBy(w -> w, Collectors.counting()));
		// get the max occuring element from 'groupByFrequency' with key value pair
		// decribed above
		Map.Entry<Integer, Long> maxEntry = groupByFrequency.entrySet().stream().max(Map.Entry.comparingByValue())
				.get();

		// get the values from the main map 'words', by 'maxEntry's key, as above key=2
		// and value=3
		// so with key 2(this time being the value of our main map 'word'), collect the
		// values
		List<Integer> list = words.entrySet().stream().filter(a -> a.getValue().equals(maxEntry.getKey()))
				.map(Map.Entry::getKey).collect(Collectors.toList());
		String mostFrequentWords = list.stream().map(Object::toString).collect(Collectors.joining(" & "));
		System.out.println("The most frequently occurring word length is " + maxEntry.getKey()
				+ ", for word lengths of " + mostFrequentWords);
	}

	// console output for WordCounter
	public static void displayResult(String fileLocation) throws IOException {
		String text = parseText(fileLocation);
		if (text == null || text.trim().isEmpty()) {
			LOG.error("No text found ");
			return;
		}
		System.out.println("Word count = " + getWordCount(text));
		System.out.println("Average word length = " + getAverageWordLength(text));
		displayListOfWordFrequencies(text);
		displayMostFrequentWord(text);

	}

	public static void main(String[] args) throws IOException {
		displayResult("src/main/resources/textFile.txt");
	}
}
