package com.se.sat.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se.sat.app.controller.AppController;

@Controller
public class AppController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
}
