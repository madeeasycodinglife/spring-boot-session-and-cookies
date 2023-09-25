package com.madeeasy.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

@RestController
public class HomeController {
    @GetMapping("/set-cookie-session")
    public String setCookieAndSessionData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String encodedValue = URLEncoder.encode("This is a cookie value with spaces", "UTF-8");
        // Set a value in a cookie
        Cookie cookie = new Cookie("cookieValue", encodedValue);
        cookie.setMaxAge(3600); // Cookie expiration time in seconds (1 hour)
        response.addCookie(cookie);

        // iterating over request[HttpServletRequest] from parameter with for-each loop
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println(attributeName + ":---------> " + request.getAttribute(attributeName));
        }

        // Set a value in a session
        HttpSession session = request.getSession();
        session.setAttribute("sessionValue", "This is a session value");

        return "Cookie and session data set successfully. Session ID: " + session.getId();
    }

    @GetMapping("/get-cookie-session")
    public String getCookieAndSessionData(HttpServletRequest request) {
        // Get the value from a cookie
        Cookie[] cookies = request.getCookies();
        String cookieValue = "Cookie not found";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cookieValue".equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }

        // Get the value from a session
        HttpSession session = request.getSession();
        String sessionValue = (String) session.getAttribute("sessionValue");

        return "Cookie Value: " + cookieValue + "<br>Session Value: " + sessionValue + "<br>Session ID: " + session.getId();
    }
}
