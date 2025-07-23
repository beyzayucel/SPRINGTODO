package com.haratres.todo.services.admin;

import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Roles;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.RolesRepository;
import com.haratres.todo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolesRepository rolesRepository;

    public ResponseEntity<Void> registerAdmin(UsersDto usersDto) {
        Users newAdmin = new Users();
        newAdmin.setEmail(usersDto.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        newAdmin.setLastName(usersDto.getLastName());
        newAdmin.setFirstName(usersDto.getFirstName());
        newAdmin.setTel(usersDto.getTel());
        Roles adminRole = rolesRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("ROLE_ADMIN didn't find"));
        newAdmin.setRoles(Set.of(adminRole));
        usersRepository.save(newAdmin);
        return ResponseEntity.ok().build();
    }
}

