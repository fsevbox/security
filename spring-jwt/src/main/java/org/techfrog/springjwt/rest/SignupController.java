package org.techfrog.springjwt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.techfrog.springjwt.model.User;
import org.techfrog.springjwt.repository.UserRepository;

@RestController
public class SignupController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public void singUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.createUser(user);
    }

}
