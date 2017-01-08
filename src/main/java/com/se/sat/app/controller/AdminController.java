package com.se.sat.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String teacherHome(Model model) {
		return "admin/home";
	}

}
