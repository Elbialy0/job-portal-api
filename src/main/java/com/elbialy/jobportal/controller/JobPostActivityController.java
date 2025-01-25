package com.elbialy.jobportal.controller;

import com.elbialy.jobportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobPostActivityController {
    private final UserService userService;

    @Autowired
    public JobPostActivityController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/dashboard")
    public ResponseEntity<?> searchJobs(){
        Object currentProfile =userService.getCurrentUserProfile();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (ResponseEntity<?>) currentProfile ;
    }
}
