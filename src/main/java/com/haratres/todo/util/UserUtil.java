package com.haratres.todo.util;

import com.haratres.todo.security.TokenProvider;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UsersRepository usersRepository;

    public Users tokenProcess(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        String email = tokenProvider.getEmailFromToken(token);
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

}

