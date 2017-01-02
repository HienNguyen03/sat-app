package com.se.sat.app;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class ProperCookieClearingLogoutHandler implements LogoutHandler {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProperCookieClearingLogoutHandler.class);


	private final List<String> cookiesToClear;

    public ProperCookieClearingLogoutHandler(String... cookiesToClear) {
        Assert.notNull(cookiesToClear, "List of cookies cannot be null");
        this.cookiesToClear = Arrays.asList(cookiesToClear);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        for (String cookieName : cookiesToClear) {
            Cookie cookie = new Cookie(cookieName, null);
            String cookiePath = request.getContextPath() + "/";
            if (!StringUtils.hasLength(cookiePath)) {
                cookiePath = "/";
            }
            
            //log.info("deleting cookie ! " + cookieName);
            
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
	
}
