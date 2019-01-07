package com.lyonguyen.news.controllers;

import com.lyonguyen.news.services.SecurityService;
import com.lyonguyen.news.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    @Autowired
    private SecurityService securityService;

    @Value("${webapp.messages.loginfail}")
    private String loginFailedMessage;

    @Value("${webapp.messages.logout}")
    private String logoutMessage;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isLoggedIn()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", loginFailedMessage);
        }
        if (logout != null) {
            model.addAttribute("message", logoutMessage);
        }

        return "login";
    }
}
