package com.haratres.todo.services.user.impl;

import com.haratres.todo.entity.Roles;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.RolesRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.services.user.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    @Override
    public Users saveUsers(Users users) {
        if (users.getRoles()==null) {
            Roles defaultRole = rolesRepository.findByName("ROLE_USER").get();
            users.setRoles(Set.of(defaultRole));
        }
        return usersRepository.save(users);
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        Users users = usersRepository.findByEmail(email).get();

        var authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(
                users.getEmail(),
                users.getPassword(),
                authorities);
    }


}
