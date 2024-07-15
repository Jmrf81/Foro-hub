package com.forohub_alura.Foro_hub.controller;

import com.forohub_alura.Foro_hub.domain.user.DataRegister;
import com.forohub_alura.Foro_hub.domain.user.User;
import com.forohub_alura.Foro_hub.domain.user.UserRepository;
import com.forohub_alura.Foro_hub.domain.user.UserResponseData;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid DataRegister dataRegister, UriComponentsBuilder uriComponentsBuilder){
        try {
            // Save new user to the database
            User user = userRepository.save(new User(dataRegister,bCryptPasswordEncoder));

            // Create response data for the registered user
            UserResponseData responseUserData = new UserResponseData(
                    user.getId(), user.getName()
            );

            // Build URI for the newly created resource
            URI url = uriComponentsBuilder.path("user/{idUser}").buildAndExpand(user.getId()).toUri();

            // Return HTTP 201 Created response with the created user's data and URI
            return ResponseEntity.created(url).body(responseUserData);
        } catch (ConstraintViolationException ex) {
            // Handle validation errors
            return ResponseEntity.badRequest().body("Validation failed: " + ex.getMessage());
        }
    }

}
