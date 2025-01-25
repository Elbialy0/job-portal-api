package com.elbialy.jobportal.controller;

import com.elbialy.jobportal.Config.SecurityConfig;
import com.elbialy.jobportal.dto.LoginDTO;
import com.elbialy.jobportal.dto.UserRegistrationDTO;
import com.elbialy.jobportal.model.User;
import com.elbialy.jobportal.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    // inject user service
    private UserService userService;
    private SecurityConfig securityConfig;
    @Autowired

    public AuthController(UserService userService,SecurityConfig securityConfig,AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.securityConfig=securityConfig;
        this.authenticationManager=authenticationManager;
    }
     @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        User user = userService.register(userRegistrationDTO);
        return ResponseEntity.ok(user);

   
     }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return a success response
            return ResponseEntity.ok(userService.getCurrentUserProfile());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    // Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Spring Security automatically handles logout
        SecurityContextHolder.clearContext(); // Clear the authentication context
        return ResponseEntity.ok("Logged out successfully");
    }

}
