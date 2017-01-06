package com.se.sat.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.dto.CustomUserDetails;
import com.se.sat.app.entity.User;


@Component
public class AppUtil {
	
	//private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);
	
	private static MessageSource messageSource;
	private static String hostAndPort;
	
	@Autowired
	public AppUtil(MessageSource messageSource){
		AppUtil.messageSource = messageSource;
	}
	
	public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey){ 
		redirectAttributes.addFlashAttribute("flashType", kind);
		redirectAttributes.addFlashAttribute("flashMessage", getMessage(messageKey));
	}
	
	public static void flashModelAttribute(Model model, String kind, String messageKey){ 
		model.addAttribute("flashType", kind);
		model.addAttribute("flashMessage", getMessage(messageKey));
	}
	
	public static String getMessage(String messageKey, Object... args){
		return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
	}
	
	@Value("${hostAndPort}")
	public void setHostAndPort(String hostAndPort){
		AppUtil.hostAndPort = hostAndPort;
	}
	
	public static String hostURL(){
		return "http://" + hostAndPort;
	}
	
	public static User getUserFromSession(){
		CustomUserDetails customUserDetails = getAuth();
		return customUserDetails == null ? null : customUserDetails.getUser();
	}
	
	public static CustomUserDetails getAuth(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null){
			Object principal = auth.getPrincipal();
			if(principal instanceof CustomUserDetails){
				return (CustomUserDetails) principal;
			}
		}
		
		return null;
	}
	
	public static void validate(boolean valid, String falseKeyCode, Object... args){
		if(!valid){
			throw new RuntimeException(getMessage(falseKeyCode, args));
		}
	}
}
