package com.epassi.rest.webservices.wordfrequencyapi.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.springframework.stereotype.Service;

@Service
public class WordFrequencyService {
	
	private final Map<String, List<String>> cache = new HashMap<>();

	/**
	 * Check whether the filepath and topk is saved in cache
	 * @param filePath
	 * @param topK
	 * @return List of top K frequent words
	 * @throws IOException
	 */
    public List<String> findTopKFrequentWords(String filePath, int topK) throws IOException {
        // Check if the result is already cached
        String cacheKey = filePath + "-" + topK;
        if (cache.containsKey(cacheKey)) {
            System.out.println("Using cached result for " + cacheKey);
            return cache.get(cacheKey);
        }

        // Read the file and calculate the top K frequent words
        List<String> result = calculateTopKFrequentWords(filePath, topK);

        // Cache the result
        cache.put(cacheKey, result);

        return result;
    }

	/**
     * Finds the top K most frequent words in a text file.
     *
     * @param filePath Path to the text file
     * @param topK     Number of top frequent words to retrieve
     * @return List of top K frequent words
     * @throws IOException If an I/O error occurs while reading the file
     */
	public List<String> calculateTopKFrequentWords(String filePath, int topK) throws IOException {

			// Map to store word frequencies
	        Map<String, Integer> wordFrequencyMap = new HashMap<>();
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                processLine(line, wordFrequencyMap);
	            }
	        }

	        // Max heap to efficiently find the top K frequent words
	        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
//	                (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())
	        		(entry1, entry2) -> {
	                    int frequencyComparison = Integer.compare(entry2.getValue(), entry1.getValue());
	                    return (frequencyComparison != 0) ? frequencyComparison : entry1.getKey().compareTo(entry2.getKey());
	                }
	        );

	        // Add all entries from the word frequency map to the max heap
	        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
	            maxHeap.offer(entry);
	        }

	        // Retrieve the top K frequent words from the max heap
	        List<String> result = new java.util.ArrayList<>();
	        while (topK-- > 0 && !maxHeap.isEmpty()) {
	        	Map.Entry<String, Integer> entry = maxHeap.poll();
//	            result.add(entry.getKey() + ": " + entry.getValue().toString());
	        	result.add(entry.getKey());
	        }

	        return result;
        
	}
	
	private void processLine(String line, Map<String, Integer> wordFrequencyMap) {
        String[] words = line.split("\\s+");
        for (String word : words) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", "").replaceAll(" ", "");
            wordFrequencyMap.put(cleanedWord, wordFrequencyMap.getOrDefault(cleanedWord, 0) + 1);
        }
    }
	
}
