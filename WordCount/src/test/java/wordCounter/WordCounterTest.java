package wordCounter;


import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import wordCounter.WordCounter;

public class WordCounterTest {

	// can test using string here directly for simplicity
	// or parsing the text everytime such as testParseText()
	String words = "1 22 22 222 222 3333 3333 3333 55555 55555 ";

	// main test for console output
	// please replace the file location or add text as a String
	@Test
	public void displayResultTest() throws IOException {
		WordCounter.displayResult("src/main/resources/textFile.txt");
	}

	@Test
	public void testParseText() throws IOException {
		String fileLocation = "src/main/resources/textFile.txt";
		String text = WordCounter.parseText(fileLocation);
		assertThat(text).isEqualTo("1 22 22 222 222 3333 3333 3333 55555 55555 ");
	}

	@Test
	public void testNullParseText() throws IOException {
		String fileLocation = null;
		// returns null with a log message for simplicity of the console output,
		// couldn't test the log message:)
		assertThat(WordCounter.parseText(fileLocation)).isNull();
	}

	@Test
	public void getWordCountTest() {
		assertThat(WordCounter.getWordCount(words)).isEqualTo(10);
		assertThat(WordCounter.getWordCount("a bb")).isEqualTo(2);
	}

	@Test
	public void deleteFullStopsTest() {
		String withFullStop = "morning.";
		assertThat(WordCounter.deleteFullStops(withFullStop)).isEqualTo("morning");
	}

	@Test
	public void getAverageWordLengthTest() {
		assertThat(WordCounter.getAverageWordLength(words)).isEqualTo(3.3);
		assertThat(WordCounter.getAverageWordLength("q ss yrr")).isEqualTo(2);
	}

	@Test
	public void getWordFrequenciesTest() {
		Map<Integer, Integer> wordsMap = WordCounter.getWordFrequencies(words);
		Map<Integer, Integer> expectedMap = new HashMap() {
			{
				put(1, 1);
				put(2, 2);
				put(3, 2);
				put(4, 3);
				put(5, 2);
			}
		};
		assertThat(wordsMap).isEqualTo(expectedMap);
	}

}
