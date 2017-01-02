package com.se.sat.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.controller.AppController;
import com.se.sat.app.dto.SignupAdminForm;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.SignupStudentForm;
import com.se.sat.app.dto.SignupTeacherForm;
import com.se.sat.app.entity.User;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;
import com.se.sat.app.validator.SignupFormValidator;

@Controller
@SessionAttributes("user")
public class AppController {	
	private static final Logger log = LoggerFactory.getLogger(AppController.class);
	private UserService userService;
	private SignupFormValidator signupFormValidator;

	@Autowired
	public AppController(UserService userService, SignupFormValidator signupFormValidator) {
		this.userService = userService;
		this.signupFormValidator = signupFormValidator;
	}

	@InitBinder("signupForm")
	protected void initSignupBuilder(WebDataBinder binder) {
		binder.setValidator(signupFormValidator);
	}

	@RequestMapping("/")
	public String index() {
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
	public String signupNext(Model model, @ModelAttribute("signupForm") @Valid SignupForm signupForm,
			BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			log.info("Error: " + result.getAllErrors().toString());
			model.addAttribute("signupForm", signupForm);
			return "signup";
		}

		if (!model.containsAttribute("user")) {
			model.addAttribute("user", signupForm);
		}

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
		
		if(!model.containsAttribute("user")){
			model.addAttribute(new SignupForm());
			return "redirect:/signup";
		}
		
		model.addAttribute(new SignupStudentForm());
		return "signup-next-student";
	}

	@RequestMapping(value = "/signup-next-1", method = RequestMethod.POST)
	public String signupStudentSubmit(Model model,
			@ModelAttribute("signupStudentForm") @Valid SignupStudentForm signupStudentForm, BindingResult result,
			@ModelAttribute("user") @Valid SignupForm signupForm, BindingResult result1,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("signupStudentForm", signupStudentForm);
			return "signup-next-student";
		}

		boolean jobDone = userService.signup(signupForm, signupStudentForm);

		if (jobDone)
			AppUtil.flash(redirectAttributes, "success", "signup.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "signup.failure");

		status.setComplete();

		return "redirect:/login";
	}
	
	@RequestMapping(value = "/signup-next-2", method = RequestMethod.GET)
	public String signupNextTeacherForm(Model model) {
		
		if(!model.containsAttribute("user")){
			model.addAttribute(new SignupForm());
			return "redirect:/signup";
		}
		
		model.addAttribute(new SignupTeacherForm());
		return "signup-next-teacher";
	}

	@RequestMapping(value = "/signup-next-2", method = RequestMethod.POST)
	public String signupTeacherSubmit(Model model,
			@ModelAttribute("signupTeacherForm") @Valid SignupTeacherForm signupTeacherForm, BindingResult result,
			@ModelAttribute("user") @Valid SignupForm signupForm, BindingResult result1,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("signupTeacherForm", signupTeacherForm);
			return "signup-next-teacher";
		}

		boolean jobDone = userService.signup(signupForm, signupTeacherForm);

		if (jobDone)
			AppUtil.flash(redirectAttributes, "success", "signup.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "signup.failure");

		status.setComplete();

		return "redirect:/login";
	}
	
	@RequestMapping(value = "/signup-next-3", method = RequestMethod.GET)
	public String signupNextAdminForm(Model model) {
		
		if(!model.containsAttribute("user")){
			model.addAttribute(new SignupForm());
			return "redirect:/signup";
		}
		
		model.addAttribute(new SignupAdminForm());
		return "signup-next-admin";
	}

	@RequestMapping(value = "/signup-next-3", method = RequestMethod.POST)
	public String signupAdminSubmit(Model model,
			@ModelAttribute("signupAdminForm") @Valid SignupAdminForm signupAdminForm, BindingResult result,
			@ModelAttribute("user") @Valid SignupForm signupForm, BindingResult result1,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("signupAdminForm", signupAdminForm);
			return "signup-next-admin";
		}

		boolean jobDone = userService.signup(signupForm, signupAdminForm);

		if (jobDone)
			AppUtil.flash(redirectAttributes, "success", "signup.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "signup.failure");

		status.setComplete();

		return "redirect:/login";
	}

}
