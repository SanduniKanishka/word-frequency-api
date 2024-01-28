package com.epassi.rest.webservices.wordfrequencyapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epassi.rest.webservices.wordfrequencyapi.service.WordFrequencyService;

@RestController
public class WordFrequencyController {
	
	private WordFrequencyService wordFrequencyService;
	
	@Autowired
	public WordFrequencyController(WordFrequencyService wordFrequencyService) {
		this.wordFrequencyService = wordFrequencyService;
	}
	
	@GetMapping("/getTopKFrequentWords")
	public List<String> serachKFrequentWords(@RequestParam String filePath, @RequestParam int topK) throws IOException{
		return wordFrequencyService.findTopKFrequentWords(filePath, topK);
	}
}
