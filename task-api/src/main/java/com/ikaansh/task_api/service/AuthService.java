
package com.ikaansh.task_api.service;

import com.ikaansh.task_api.entity.AppUser;
import com.ikaansh.task_api.repository.AppUserRepository;
import com.ikaansh.task_api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public AppUser registerUser(AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return appUserRepository.save(user);
    }


    public String loginUser(String username, String password) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return jwtService.generateToken(user.getUsername());
    }
}
