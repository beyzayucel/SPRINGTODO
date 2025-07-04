package com.haratres.todo.controller;

import com.haratres.todo.config.TokenProvider;
import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Roles;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.RolesRepository;
import com.haratres.todo.repository.UsersRepository;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersDto loginUserDto) {
        Users user = usersRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User didn't find."));

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password.");
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                authorities
        );

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(token);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UsersDto request) {
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Roles adminRole = rolesRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> rolesRepository.save(new Roles("ROLE_ADMIN")));

        Users newAdmin = new Users();
        newAdmin.setEmail(request.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
        newAdmin.setLastName(request.getLastName());
        newAdmin.setFirstName(request.getFirstName());
        newAdmin.setTel(request.getTel());
        newAdmin.setRoles(Set.of(adminRole));

        usersRepository.save(newAdmin);

        return ResponseEntity.ok("Admin added successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> userDtos = usersRepository.findAll().stream()
                .map(user -> {
                    List<TasksDto> taskDtos = user.getTasks().stream()
                            .map(task -> new TasksDto(
                                    task.getCreatedDate(),
                                    task.getDescription(),
                                    task.getImportant(),
                                    task.getStatus(),
                                    task.getTitle()
                            ))
                            .collect(Collectors.toList());

                    return new UsersDto(
                            user.getPassword(),
                            user.getEmail(),
                            user.getFirstName(),
                            user.getLastName(),
                            taskDtos,
                            user.getTel()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }
    }

