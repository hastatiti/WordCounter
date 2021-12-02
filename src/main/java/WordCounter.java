import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCounter {

    // entry point that executes methods in required order
    public static void wordCounter() throws Exception {
        Map<Integer, Integer> words = parseText("src/main/resources/file.txt");
//        Map<Integer, Integer> words = parseText("src/main/resources/bible.txt");
//        Map<Integer, Integer> words = parseText("src/main/resources/empty.txt");
//        Map<Integer, Integer> words = parseText("src/main/resources/notExists.txt");
        int wordCount = wordCount(words);
        System.out.println("Word count = " + wordCount);
        int sumOfWordLengths = sumOfWordLengths(words);
        double averageWordLength = averageWordLength(sumOfWordLengths, wordCount);
        System.out.println("Average word length = " + String.format("%.3f", averageWordLength));
        printFrequencyPairs(words);
        printMostFrequentWord(words);
    }

    // create a map of word length and frequency pairs such as 'Number of words of length 4 is 2' ...
    public static Map<Integer, Integer> parseText(String fileLocation) throws Exception {
        Map<Integer, Integer> words = new HashMap<>();
        BufferedReader txtReader = null;
        try {
            txtReader = new BufferedReader(new FileReader(fileLocation));
            String line;
            while ((line = txtReader.readLine()) != null) {
                String[] wordArray = removePunctuation(line);
                for (var v : wordArray) {
                    int length = v.length();
                    if (!words.containsKey(length)) {
                        words.put(length, 1);
                    } else
                        words.put(length, words.get(length) + 1);
                }
            }
        } catch (IOException e) {
            throw new IOException("File not found,  please check file location ");
        } catch (Exception e) {
            throw e;
        }
        return words;
    }

    // remove punctuations except '/' and '&'
    private static String[] removePunctuation(String line) {
        return line.replaceAll("[\\p{Punct}&&[^/&]]", "").split(" ");
    }

    public static int wordCount(Map<Integer, Integer> words) {
        return words.entrySet().stream().mapToInt((w) -> w.getValue()).sum();
    }

    public static int sumOfWordLengths(Map<Integer, Integer> words) {
        return words.entrySet().stream().mapToInt((w) -> w.getKey() * w.getValue()).sum();
    }

    // handle division by 0
    public static double averageWordLength(int sumOfWordLengths, int wordCount) {
        return wordCount == 0 ? 0 : (double) sumOfWordLengths / wordCount;
    }

    public static void printFrequencyPairs(Map<Integer, Integer> words) {
        words.entrySet().forEach(entry -> {
            System.out.println("Number of words of length " + entry.getKey() + " is " + entry.getValue());
        });
    }

    public static void printMostFrequentWord(Map<Integer, Integer> words) {
        int maxValueInMap = (Collections.max(words.values()));
        // collect list of most frequent values in the map
        List<Integer> list = words.entrySet().stream().filter(a -> a.getValue().equals(maxValueInMap))
                .map(Map.Entry::getKey).collect(Collectors.toList());
        // remove commas and add '&' as required
        String wordLengts = list.stream().map(Object::toString).collect(Collectors.joining(" & "));
        System.out.println("The most frequently occurring word length is " + maxValueInMap +
                ", for word lengths of " + wordLengts);
    }

    public static void main(String[] args) throws Exception {
        wordCounter();
    }

}
