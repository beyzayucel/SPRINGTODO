package com.example.demo.services.user.impl;

import com.example.demo.dto.RolesDto;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.services.user.UsersService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service("userService")
public class UsersServiceImpl implements UsersService, UserDetailsService {

    UsersRepository usersRepository;
    ModelMapper modelMapper;
    RolesRepository rolesRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,ModelMapper modelMapper,RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.modelMapper=modelMapper;
        this.rolesRepository=rolesRepository;
    }

    @Override
    public List<Users> allGetUsers() {
        List<Users> users=usersRepository.findAll();
        return users;
    }

    @Override
    public Users getById(int id) {
        Optional<Users> u=usersRepository.findById(id);
        Users users=null;
        if(u.isPresent()){
            users=u.get();
        }
        else{
            throw new RuntimeException("Girilen id bulunamadı.");
        }
        return users;
    }

    @Override
    public void delete(int id) {
        usersRepository.deleteById(id);
    }


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

    public List<Map<String, Object>> getAllUsersWithTasks() {
        List<Users> users = usersRepository.findAll();

        return users.stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("email", user.getEmail());

            // Kullanıcının görevlerini sadece görev adları olarak listele
            List<String> taskNames = user.getTasks().stream()
                    .map(Tasks::getTitle)
                    .collect(Collectors.toList());

            map.put("tasks", taskNames);
            return map;
        }).collect(Collectors.toList());
    }



}
