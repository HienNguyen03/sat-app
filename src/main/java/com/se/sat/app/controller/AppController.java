package com.se.sat.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.controller.AppController;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.SignupStudentForm;
import com.se.sat.app.dto.SignupTeacherForm;
import com.se.sat.app.entity.User;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;

@Controller
@SessionAttributes("user")
public class AppController {
	
	// add some comment - test 2

	private static final Logger log = LoggerFactory.getLogger(AppController.class);
	private UserService userService;

	@Autowired
	public AppController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String index() {
		log.info("run controller");
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm(Model model) {
		model.addAttribute(new SignupForm());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupNext(Model model, @ModelAttribute("signupForm") @Valid SignupForm signupForm, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			log.info(result.getAllErrors().toString());
			model.addAttribute("signupForm", signupForm);
			return "signup";
		}

		// userService.signup(signupForm);
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", signupForm);
		}
		//redirectAttributes.addFlashAttribute("signupForm", signupForm);

		String role = (String) request.getParameter("roleSelection").toUpperCase();
		if (role.equals(User.Role.STUDENT.toString())) {
			return "redirect:/signup-next-1";
		} else if (role.equals(User.Role.TEACHER.toString())) {
			return "redirect:/signup-next-2";
		} else {
			return "redirect:/signup-next-3";
		}

	}

	@RequestMapping(value = "/signup-next-1", method = RequestMethod.GET)
	public String signupNextStudentForm(Model model) {
		model.addAttribute(new SignupStudentForm());
		return "signup-next-student";
	}

	@RequestMapping(value = "/signup-next-1", method = RequestMethod.POST)
	public String signupStudentSubmit(@ModelAttribute("user") @Valid SignupForm signupForm, BindingResult result1, @ModelAttribute("signupStudentForm") @Valid SignupStudentForm signupStudentForm, BindingResult result,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		
		
		log.info("2: " + signupForm.toString());
		
		
		if (result.hasErrors()) {
			return "signup-next-student";
		}

		// userService.signup(signupForm);

		// redirectAttributes.addFlashAttribute("signup_info", "success");
		// redirectAttributes.addFlashAttribute("signup_message",
		// messageSource.getMessage("message.success", new Object[0],
		// LocaleContextHolder.getLocale()));

		// AppUtil.flash(redirectAttributes, "success", "message.success");

		status.setComplete();
		
		return "redirect:/";
	}

}
