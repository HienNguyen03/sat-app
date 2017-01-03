package com.se.sat.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.sat.app.entity.Course;

@Controller
@RequestMapping("/student")
public class StudentController {

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model){
		Course course = new Course();
		
		model.addAttribute(course);
		return "student/home";
	}
	
	
	
}
