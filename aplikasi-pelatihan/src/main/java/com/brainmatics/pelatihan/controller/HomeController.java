package com.brainmatics.pelatihan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/api/me")
    @ResponseBody
    public Authentication currentUser(Authentication currentUser){
        return currentUser;
    }
}
