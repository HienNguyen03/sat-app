package com.se.sat.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se.sat.app.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
	
	private TeacherService teacherService;
	
	@Autowired
	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	@RequestMapping(value="/{teacherId}")
	public String teacherHome(@PathVariable Integer teacherId, Model model){
		model.addAttribute(teacherService.findTeacherInfo(teacherId));
		return "/teacher/home";
	}
	


}
