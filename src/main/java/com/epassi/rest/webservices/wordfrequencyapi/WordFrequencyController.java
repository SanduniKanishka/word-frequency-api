package com.epassi.rest.webservices.wordfrequencyapi;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordFrequencyController {
	
	private WordFrequencyService wordFrequencyService;
	
	@Autowired
	public WordFrequencyController(WordFrequencyService wordFrequencyService) {
		this.wordFrequencyService = wordFrequencyService;
	}
	
}
