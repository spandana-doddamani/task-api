
package com.ikaansh.task_api.authh;

import com.ikaansh.task_api.entity.AppUser;
import com.ikaansh.task_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthhController {

    private final AuthService authService;

    public AuthhController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String testAuth() {
        return "Authentication endpoint is working!";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        try {
            AppUser savedUser = authService.registerUser(user);
            savedUser.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.loginUser(
                    request.getUsername(), request.getPassword());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "tokenType", "Bearer"
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
