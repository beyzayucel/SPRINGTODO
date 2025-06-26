package com.example.demo.controller;


import com.example.demo.config.TokenProvider;
import com.example.demo.dto.TasksDto;
import com.example.demo.dto.UsersDto;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.services.user.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AdminController(UsersRepository usersRepository, UsersService usersService,
                           RolesRepository rolesRepository,
                           PasswordEncoder passwordEncoder,
                           TokenProvider tokenProvider) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersDto loginUserDto) {
        Users user = usersRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Şifre yanlış");
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                authorities
        );

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(token);
    }



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

        return ResponseEntity.ok("Admin registered successfully");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> userDtos = usersRepository.findAll().stream()
                .map(user -> {
                    List<TasksDto> taskDtos = user.getTasks().stream()
                            .map(task -> new TasksDto(
                                    task.getCreatedDate(),
                                    task.getDescription(),
                                    task.getImportant(),
                                    task.getStatus(),
                                    task.getTitle(),
                                    null // gerekirse kullanıcı emaili veya başka info ekleyebilirsin
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


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users-tasks")
    public List<Map<String, Object>> getAllUsersWithTasks() {
        return usersService.getAllUsersWithTasks();
    }



}

