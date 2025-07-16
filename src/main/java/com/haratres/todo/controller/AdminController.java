package com.haratres.todo.controller;

import com.haratres.todo.dto.*;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.UserImage;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.security.TokenProvider;
import com.haratres.todo.services.admin.AdminService;
import com.haratres.todo.validators.LoginValidators;
import com.haratres.todo.validators.UsersValidators;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    @Autowired
    LoginValidators loginValidators;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersValidators usersValidators;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody UsersDto usersDto) {
        validate(usersDto, "user", loginValidators);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersDto.getEmail(), usersDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UsersDto usersDto) {
        validate(usersDto, "user", usersValidators);
        return adminService.registerAdmin(usersDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<AdminGetUsersDto>> getAllUsers() {
        List<Users> users = usersRepository.findByRoles_Name("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER didn't find"));
        List<AdminGetUsersDto> adminGetUsersDtoList = new ArrayList<>();
        for (Users user : users) {
            List<Tasks> tasksList = user.getTasks();
            List<TasksDto> tasksDto = tasksList.stream().map(t -> modelMapper().map(t, TasksDto.class)).collect(Collectors.toList());
            List<UserImage> userImages = user.getUserImages();
            List<ImageDto> imageDto = userImages.stream().map(s -> modelMapper().map(s, ImageDto.class)).collect(Collectors.toList());
            AdminGetUsersDto adminDto = new AdminGetUsersDto(user.getEmail(), user.getFirstName(), user.getLastName(), tasksDto, user.getTel(), imageDto);
            adminGetUsersDtoList.add(adminDto);
        }
        return ResponseEntity.ok(adminGetUsersDtoList);
    }
}
