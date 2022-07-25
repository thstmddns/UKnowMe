package com.ssafy.uknowme.controller;

import com.ssafy.uknowme.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginId(@ModelAttribute User user) {
        if (loginService.login(user)){
            return "redirect:/";
        }
        return "login";
    }
}
