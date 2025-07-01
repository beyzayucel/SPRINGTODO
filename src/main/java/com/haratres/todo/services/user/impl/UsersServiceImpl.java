package com.haratres.todo.services.user.impl;

import com.haratres.todo.entity.Roles;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.RolesRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.services.user.UsersService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;


    @Override
    public Users saveUsers(Users users) {
        if (users.getRoles() == null || users.getRoles().isEmpty()) {
            Roles defaultRole = rolesRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER role not found in DB"));
            users.setRoles(Set.of(defaultRole));
        }
        return usersRepository.save(users);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

}
