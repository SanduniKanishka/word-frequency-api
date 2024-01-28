package com.epassi.rest.webservices.wordfrequencyapi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class WordFrequencyService {

	/**
     * Finds the top K most frequent words in a text file.
     *
     * @param filePath Path to the text file
     * @param topK     Number of top frequent words to retrieve
     * @return List of top K frequent words
     * @throws IOException If an I/O error occurs while reading the file
     */
	public List<String> findTopKFrequentWords(String filePath, int topK) throws IOException {
			// Read the entire file into a String
			String text = new String(Files.readAllBytes(Paths.get(filePath)));
	        
			// Split the text into words using whitespace as a delimiter
			String[] words = text.split("\\s+");

			// Map to store word frequencies
	        Map<String, Integer> wordFrequencyMap = new HashMap<>();

	        for (String word : words) {
	        	// Clean the word by converting to lowercase and removing non-alphabetic characters
	            String cleanedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); 
	            wordFrequencyMap.put(cleanedWord, wordFrequencyMap.getOrDefault(cleanedWord, 0) + 1);
	        }

	        // Max heap to efficiently find the top K frequent words
	        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
	                (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())
	        );

	        // Add all entries from the word frequency map to the max heap
	        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
	            maxHeap.offer(entry);
	        }

	        // Retrieve the top K frequent words from the max heap
	        List<String> result = new java.util.ArrayList<>();
	        while (topK-- > 0 && !maxHeap.isEmpty()) {
	            result.add(maxHeap.poll().getKey());
	        }

	        return result;
        
	}
	
}
