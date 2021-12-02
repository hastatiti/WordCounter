import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

public class WordCounterTest {
  // This test uses file.txt given in resources folder with the following structure :
//    a a
//    ands ands ands
//    andsy andsy andsy

    @Test
    public void parseNonExistingFile() {
        IOException thrown = Assertions.assertThrows(IOException.class, () -> {
            WordCounter.parseText("src/main/resources/notExists.txt");
        });
        Assertions.assertEquals("File not found,  please check file location ", thrown.getMessage());
    }

    @Test
    public void parseEmptyFile() throws Exception {
        Assertions.assertDoesNotThrow(() -> WordCounter.parseText("src/main/resources/empty.txt"));
    }

    @Test
    public void countWordsEmptyFile() throws Exception {
        var words = WordCounter.parseText("src/main/resources/empty.txt");
        int wordCount = WordCounter.wordCount(words);
        assert wordCount == 0;
        int sumOfWordLengths = WordCounter.sumOfWordLengths(words);
        double averageWordLength = WordCounter.averageWordLength(sumOfWordLengths, wordCount);
        assert averageWordLength == 0.000;
    }
    @Test
    public void parseFile() throws Exception{
        var words = WordCounter.parseText("src/main/resources/file.txt");
        int wordCount = WordCounter.wordCount(words);
        assert wordCount == 8;
        int sumOfWordLengths = WordCounter.sumOfWordLengths(words);
        double averageWordLength = WordCounter.averageWordLength(sumOfWordLengths, wordCount);
        assert averageWordLength == 3.625;
    }
}
