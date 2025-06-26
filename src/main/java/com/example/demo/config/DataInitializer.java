package com.example.demo.config;

import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Roles adminRole = rolesRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> rolesRepository.save(new Roles("ROLE_ADMIN")));

        if (usersRepository.findByEmail("admin@gmail.com").isEmpty()) {
            Users user = new Users();
            user.setEmail("admin@gmail.com");
            // Şifreyi hashleyerek kaydet
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Set.of(adminRole));
            usersRepository.save(user);
        }
    }
}




