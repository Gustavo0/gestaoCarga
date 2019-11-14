package com.gestao.cargas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
	
	@RequestMapping("/")
	@ResponseBody
	@GetMapping
	public ResponseEntity<String> helloWorld() {
		return ResponseEntity.ok().body("Hello World!");
	}

}
