package com.haratres.todo.controller;

import com.haratres.todo.config.TokenProvider;
import com.haratres.todo.dto.AuthTokenDto;
import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Users;
import com.haratres.todo.services.user.UsersService;
import com.haratres.todo.validators.UsersValidators;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersValidators usersValidators;

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody UsersDto loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.createToken(authentication);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }


    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsersDto usersDto, Errors errors) {

        usersValidators.validate(usersDto,errors);

        if (errors.hasErrors()) {
            List<Map<String, String>> errorList = errors.getAllErrors().stream()
                    .map(error -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("code", error.getCode());
                        map.put("message", error.getDefaultMessage());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorList);
        }

        Users user = new Users();
        user.setEmail(usersDto.getEmail());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        user.setTel(usersDto.getTel());
        user.setFirstName(usersDto.getFirstName());
        user.setLastName(usersDto.getLastName());

        Users savedUser = usersService.saveUsers(user);

        return ResponseEntity.ok(savedUser);
    }



}