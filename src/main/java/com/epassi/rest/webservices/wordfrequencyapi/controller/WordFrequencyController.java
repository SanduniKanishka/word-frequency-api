package com.epassi.rest.webservices.wordfrequencyapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epassi.rest.webservices.wordfrequencyapi.service.WordFrequencyService;

@RestController
@RequestMapping("/api/v1")
public class WordFrequencyController {
	
	private WordFrequencyService wordFrequencyService;
	
	@Autowired
	public WordFrequencyController(WordFrequencyService wordFrequencyService) {
		this.wordFrequencyService = wordFrequencyService;
	}
	
	@GetMapping("/TopFrequentWords")
	@PreAuthorize("hasRole('USER')")
	public List<String> serachKFrequentWords(@RequestParam String filePath, @RequestParam int topK) throws IOException{
		return wordFrequencyService.findTopKFrequentWords(filePath, topK);
	}
}
