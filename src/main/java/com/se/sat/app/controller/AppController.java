package com.se.sat.app.controller;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.controller.AppController;
import com.se.sat.app.dto.SignupAdminForm;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.SignupStudentForm;
import com.se.sat.app.dto.SignupTeacherForm;
import com.se.sat.app.dto.EditProfileAdminForm;
import com.se.sat.app.dto.EditProfileStudentForm;
import com.se.sat.app.dto.EditProfileTeacherForm;
import com.se.sat.app.entity.Admin;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;
import com.se.sat.app.validator.SignupFormValidator;

@Controller
@SessionAttributes({ "ssSignupForm", "ssSignupRole" })
public class AppController {

	private static final Logger log = LoggerFactory.getLogger(AppController.class);
	private UserService userService;
	private SignupFormValidator signupFormValidator;
	private AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	public AppController(UserService userService, SignupFormValidator signupFormValidator,
			AuthenticationTrustResolver authenticationTrustResolver) {
		this.userService = userService;
		this.signupFormValidator = signupFormValidator;
		this.authenticationTrustResolver = authenticationTrustResolver;
	}

	@InitBinder("signupForm")
	protected void initSignupBuilder(WebDataBinder binder) {
		binder.setValidator(signupFormValidator);
	}

	@RequestMapping("/")
	public String index() {
		//log.info("remember-me? " + isRememberMeAuthenticated());
		return "index";
	}

	public boolean isRememberMeAuthenticated() {
		// Check authentication exists
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}

	/**
	 * This method handles logout requests. Toggle the handlers if you are
	 * RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response,
			SessionStatus status) {
		log.info("Logout control is running !");

		if (model.containsAttribute("user")) {
			status.setComplete();
			model.asMap().remove("user");
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		session.invalidate();
		return "redirect:/login?logout";
	}

	/**
	 * This method handles login GET requests. If the user is already logged-in
	 * and tries to goto login page again, will be redirected to the home page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Principal principle) {

		if (error != null) {
			model.addAttribute("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out successfully.");
		}

		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			return "redirect:/";
		}
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in]
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

	/**
	 * This method handles access denied redirect
	 */
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinUser", getPrincipal());
		return "error/access-denied";
	}

	@RequestMapping(value = "/session-expired", method = RequestMethod.GET)
	public String sessionExpiredPage() {
		return "error/session-expired";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm(Model model) {
		model.addAttribute(new SignupForm());
		return "signup/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupNext(Model model, @ModelAttribute("signupForm") @Valid SignupForm signupForm,
			BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			log.info("Error: " + result.getAllErrors().toString());
			model.addAttribute("signupForm", signupForm);
			return "signup/signup";
		}

		// override if there was any ssSignupForm object
		model.addAttribute("ssSignupForm", signupForm);
		
		String role = (String) request.getParameter("roleSelection").toUpperCase();
		if (role.equals(User.Role.STUDENT.toString()))
			model.addAttribute("ssSignupRole", Role.STUDENT);
		else if (role.equals(User.Role.TEACHER.toString()))
			model.addAttribute("ssSignupRole", Role.TEACHER);
		else if (role.equals(User.Role.ADMIN.toString()))
			model.addAttribute("ssSignupRole", Role.ADMIN);
		
		return "redirect:/signup-next";

	}

	@RequestMapping(value = "/signup-next", method = RequestMethod.GET)
	public String signupNextForm(Model model, @ModelAttribute("ssSignupRole") Role userSelectionRole) {
		
		if (!model.containsAttribute("ssSignupForm")) {
			model.addAttribute(new SignupForm());
			return "redirect:/signup";
		}
		
		if (userSelectionRole.equals(Role.STUDENT)) {
			model.addAttribute(new SignupStudentForm());
			return "signup/signup-next-student";
		} else if(userSelectionRole.equals(Role.TEACHER)) {
			model.addAttribute(new SignupTeacherForm());
			return "signup/signup-next-teacher";
		} else if(userSelectionRole.equals(Role.ADMIN)){
			model.addAttribute(new SignupAdminForm());
			return "signup/signup-next-admin";
		} else {
			return null;
		}
		
	}

	@RequestMapping(value = "/signup-next", method = RequestMethod.POST)
	public String signupSubmit(Model model,
			@ModelAttribute("signupStudentForm") @Valid SignupStudentForm signupStudentForm, BindingResult result1,
			@ModelAttribute("signupTeacherForm") @Valid SignupTeacherForm signupTeacherForm, BindingResult result2,
			@ModelAttribute("signupAdminForm") @Valid SignupAdminForm signupAdminForm, BindingResult result3,
			@ModelAttribute("ssSignupForm") SignupForm signupForm,
			@ModelAttribute("ssSignupRole") Role userSelectionRole,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		boolean jobDone = false;

		if (userSelectionRole.equals(Role.STUDENT)) {
			if (result1.hasErrors()) {
				model.addAttribute("signupStudentForm", signupStudentForm);
				return "signup/signup-next-student";
			}

			jobDone = userService.signup(signupForm, signupStudentForm);
			
		} else if(userSelectionRole.equals(Role.TEACHER)) {
			if (result2.hasErrors()) {
				model.addAttribute("signupTeacherForm", signupTeacherForm);
				return "signup/signup-next-teacher";
			}

			jobDone = userService.signup(signupForm, signupTeacherForm);
			
		} else if(userSelectionRole.equals(Role.ADMIN)) {
			if (result3.hasErrors()) {
				model.addAttribute("signupAdminForm", signupAdminForm);
				return "signup/signup-next-admin";
			}

			jobDone = userService.signup(signupForm, signupAdminForm);
			
		}
		
		if (jobDone)
			AppUtil.flash(redirectAttributes, "success", "signup.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "signup.failure");

		status.setComplete();

		return "redirect:/login";

	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profilePage(Model model) {
		User user = AppUtil.getUserFromSession();

		if (user.isStudent()) {
			Student student = user.getStudent();
			model.addAttribute(student);
			return "profile/profile-student";
		} else if (user.isTeacher()) {
			Teacher teacher = user.getTeacher();
			model.addAttribute(teacher);
			return "profile/profile-teacher";
		} else if (user.isAdmin()) {
			Admin admin = user.getAdmin();
			model.addAttribute(admin);
			return "profile/profile-admin";
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editProfile(Model model) {
		User user = AppUtil.getUserFromSession();

		if (user.isStudent()) {
			EditProfileStudentForm editProfileStudentForm = new EditProfileStudentForm();
			editProfileStudentForm.setFirstname(user.getStudent().getFirstname());
			editProfileStudentForm.setLastname(user.getStudent().getLastname());
			editProfileStudentForm.setEmail(user.getStudent().getEmail());
			editProfileStudentForm.setAcademicGroup(user.getStudent().getAcademicGroup());

			model.addAttribute(editProfileStudentForm);
			return "profile/profile-student-edit";
		} else if (user.isTeacher()) {
			EditProfileTeacherForm editProfileTeacherForm = new EditProfileTeacherForm();
			editProfileTeacherForm.setFirstname(user.getTeacher().getFirstname());
			editProfileTeacherForm.setLastname(user.getTeacher().getLastname());
			editProfileTeacherForm.setEmail(user.getTeacher().getEmail());
			editProfileTeacherForm.setDepartment(user.getTeacher().getDepartment());

			model.addAttribute(editProfileTeacherForm);
			return "profile/profile-teacher-edit";
		} else if (user.isAdmin()) {
			EditProfileAdminForm editProfileAdminForm = new EditProfileAdminForm();
			editProfileAdminForm.setFirstname(user.getAdmin().getFirstname());
			editProfileAdminForm.setLastname(user.getAdmin().getLastname());
			editProfileAdminForm.setEmail(user.getAdmin().getEmail());

			model.addAttribute(editProfileAdminForm);
			return "profile/profile-admin-edit";
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
	public String edit(Model model,
			@ModelAttribute("editProfileStudentForm") @Valid EditProfileStudentForm editProfileStudentForm,
			BindingResult result1,
			@ModelAttribute("editProfileTeacherForm") @Valid EditProfileTeacherForm editProfileTeacherForm,
			BindingResult result2,
			@ModelAttribute("editProfileAdminForm") @Valid EditProfileAdminForm editProfileAdminForm,
			BindingResult result3, RedirectAttributes redirectAttributes, SessionStatus status)
			throws ServletException {

		User user = AppUtil.getUserFromSession();
		boolean jobDone = false;

		if (user.isStudent()) {
			if (result1.hasErrors()) {
				model.addAttribute("editProfileStudentForm", editProfileStudentForm);
				return "profile/profile-student-edit";
			}

			// check Password
			boolean passwordMatched = userService.compareUserPassword(editProfileStudentForm.getPassword());
			if (!passwordMatched) {
				result1.rejectValue("password", "passwordIsWrong");
				model.addAttribute("editProfileStudentForm", editProfileStudentForm);
				return "profile/profile-student-edit";
			}

			jobDone = userService.updateStudentProfile(editProfileStudentForm);

		} else if (user.isTeacher()) {
			if (result2.hasErrors()) {
				model.addAttribute("editProfileTeacherForm", editProfileTeacherForm);
				return "profile/profile-teacher-edit";
			}

			// check Password
			boolean passwordMatched = userService.compareUserPassword(editProfileTeacherForm.getPassword());
			if (!passwordMatched) {
				result2.rejectValue("password", "passwordIsWrong");
				model.addAttribute("editProfileTeacherForm", editProfileTeacherForm);
				return "profile/profile-teacher-edit";
			}

			jobDone = userService.updateTeacherProfile(editProfileTeacherForm);

		} else if (user.isAdmin()) {
			if (result3.hasErrors()) {
				model.addAttribute("editProfileAdminForm", editProfileAdminForm);
				return "profile/profile-admin-edit";
			}

			// check Password
			boolean passwordMatched = userService.compareUserPassword(editProfileAdminForm.getPassword());
			if (!passwordMatched) {
				result3.rejectValue("password", "passwordIsWrong");
				model.addAttribute("editProfileAdminForm", editProfileAdminForm);
				return "profile/profile-admin-edit";
			}

			jobDone = userService.updateAdminProfile(editProfileAdminForm);
		}

		if (jobDone)
			AppUtil.flash(redirectAttributes, "success", "editProfile.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "editProfile.failure");

		status.setComplete();

		return "redirect:/profile";
	}

}
