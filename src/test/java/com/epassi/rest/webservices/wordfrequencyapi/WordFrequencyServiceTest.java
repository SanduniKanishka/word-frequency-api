package com.epassi.rest.webservices.wordfrequencyapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.epassi.rest.webservices.wordfrequencyapi.service.WordFrequencyService;

@SpringBootTest
public class WordFrequencyServiceTest {
	
	private final WordFrequencyService wordFrequencyService = new WordFrequencyService();

	 
	 @BeforeEach
	 void setUp() {
	    MockitoAnnotations.openMocks(this);
	 }
	 
	@Test
    public void findTopKFrequentWords_ValidInput_TopKWords() throws IOException {
        // Create a temporary test file with sample text
        String filePath = createTempTestFile("Software engineering is an engineering-based approach to software "
        		+ "development. A software engineer is a person who applies the engineering "
        		+ "design process to design, develop, test, maintain, and evaluate computer software. "
        		+ "The term programmer is sometimes used as a synonym, but may emphasize software "
        		+ "implementation over design and can also lack connotations of engineering education or skills");

        // Test with K=3 for top 3 frequent words
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 3);

        // Check if the result is as expected
        assertEquals(List.of("software", "a", "design"), topWords);
    }
	 
	@Test
    public void findTopKFrequentWords_EmptyFile_EmptyList() throws IOException {
        // Create a temporary empty test file
        String filePath = createTempTestFile("");

        // Test with K=5 for an empty file
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 5);

        // Check if the result is an empty list
        assertTrue(topWords.isEmpty());
    }
	
	@Test
    public void findTopKFrequentWords_FileNotFound_ThrowIOException() {
        // Test with a non-existing file
        assertThrows(IOException.class, () -> wordFrequencyService.findTopKFrequentWords("nonexistent.txt", 3));
    }
	
	@Test
    public void findTopKFrequentWords_FileContainsDuplicates_SingleWord() throws IOException {
        // Create a temporary test file with duplicate words
        String filePath = createTempTestFile("word word word word");

        // Test with K=1 for the top frequent word
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 1);

        // Check if the result is a list with a single word ("word")
        assertEquals(List.of("word"), topWords);
    }

	@Test
    public void findTopKFrequentWords_SameFrequency_WordsInAlphabeticOrder() throws IOException {
        // Create a temporary test file with words having the same frequency
        String filePath = createTempTestFile("software Engineering is software Engineering");

        // Test with K=2 for the top 2 frequent words
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 2);
        
        assertEquals(List.of("engineering", "software"), topWords);
        assertEquals(2, topWords.size());
    }
	
	@Test
    public void findTopKFrequentWords_LessWordsThanK_AllUniqueWords() throws IOException {
        // Create a temporary test file with fewer unique words than K
        String filePath = createTempTestFile("Software engineering is an engineering");

        // Test with K=6 for the top 6 frequent words
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 6);

        // Check if the result is as expected (contains all unique words)
        assertEquals(List.of("engineering", "an", "is", "software"), topWords);
        assertEquals(4, topWords.size());
    }
	
	@Test
    public void findTopKFrequentWords_NonAlphabeticCharacters_IgnoreNonAlphabets() throws IOException {
        // Create a temporary test file with non-alphabetic characters
        String filePath = createTempTestFile("Software! engineering! ! is an engineering");

        // Test with K=3 for the top 3 frequent words
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 3);

        // Check if the result is as expected (non-alphabetic characters are ignored)
        assertEquals(List.of("engineering", "an", "is"), topWords);
        assertEquals(3, topWords.size());
    }
	
	@Test
    public void findTopKFrequentWords_LargeFile_TopKFrequentWords() throws IOException {
        // Create a temporary test file with a large number of words
        String filePath = createTempTestFile(getContent().toString().trim());

        // Test with K=1 for the top frequent word
        List<String> topWords = wordFrequencyService.findTopKFrequentWords(filePath, 1);

        // Check if the result is as expected (contains only "word")
        assertEquals(List.of("word"), topWords);
    }
	
	private StringBuilder getContent() {
		StringBuilder content = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            content.append("word ");
        }
        return content;
	}
	private String createTempTestFile(String content) throws IOException {
        Path tempFilePath = Files.createTempFile("testFile", ".txt");
        Files.write(tempFilePath, content.getBytes());
        return tempFilePath.toString();
	}
	 
}

