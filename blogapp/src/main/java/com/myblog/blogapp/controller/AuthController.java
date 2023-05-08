package com.myblog.blogapp.controller;

import com.myblog.blogapp.entities.Role;
import com.myblog.blogapp.entities.User;
import com.myblog.blogapp.payload.LoginDto;
import com.myblog.blogapp.payload.SignUpDto;
import com.myblog.blogapp.repository.RoleRepository;
import com.myblog.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
   private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signin")
     public ResponseEntity<String>AuthicateUser(@RequestBody LoginDto loginDto)
     {
         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(loginDto.getUsernameorEmail(), loginDto.getPassword())
         );
         SecurityContextHolder.getContext().setAuthentication(authentication);
         return new ResponseEntity<>("User login successfully", HttpStatus.OK);
     }
     @PostMapping("/signup")
     public ResponseEntity<?>registration(@RequestBody SignUpDto signUpDto)
     {
         if(userRepository.existsByEmail(signUpDto.getEmail()))
         {
             return new ResponseEntity<>("already exists email",HttpStatus.BAD_REQUEST);
         }
         if(userRepository.existsByUsername(signUpDto.getUsername()))
         {
             return new ResponseEntity<>("already username is exists",HttpStatus.BAD_REQUEST);
         }
         User user=new User();
         user.setEmail(signUpDto.getEmail());
         user.setName(signUpDto.getName());
         user.setUsername(signUpDto.getUsername());
         user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

         Role roles=roleRepository.findByName("ROLE_ADMIN").get();
             user.setRoles(Collections.singleton(roles));

         User save = userRepository.save(user);
         return new ResponseEntity<>("user registration successfully",HttpStatus.CREATED);

     }
}
