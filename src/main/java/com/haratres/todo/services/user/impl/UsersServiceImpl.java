package com.haratres.todo.services.user.impl;

import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Roles;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.RolesRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.services.user.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users registerUser(UsersDto usersDto) {
        Users user = new Users();
        user.setEmail(usersDto.getEmail());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        user.setTel(usersDto.getTel());
        user.setFirstName(usersDto.getFirstName());
        user.setLastName(usersDto.getLastName());
        Roles defaultRole = rolesRepository.findByName("ROLE_USER").orElseThrow();
        user.setRoles(Set.of(defaultRole));
        return usersRepository.save(user);
    }
    @Override
    public Users updateUser(Users newUser, String email) {
        Users updateUser = usersRepository.findByEmail(email).orElseThrow();
        if (!newUser.getEmail().isBlank()) {
            updateUser.setEmail(newUser.getEmail());
        }
        if (!newUser.getTel().isBlank()) {
            updateUser.setTel(newUser.getTel());
        }
        if (!newUser.getFirstName().isBlank()) {
            updateUser.setFirstName(newUser.getFirstName());
        }
        if (!newUser.getLastName().isBlank()) {
            updateUser.setLastName(newUser.getLastName());
        }
        if (!(newUser.getPassword().isBlank()) && !newUser.getPassword().isBlank()) {
            updateUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        return usersRepository.save(updateUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User didn't find."));
        List<GrantedAuthority> authorities = users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new User(users.getEmail(), users.getPassword(), authorities);
    }
}