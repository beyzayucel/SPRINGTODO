package com.example.demo.util;

import com.example.demo.config.TokenProvider;
import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.services.task.TasksService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    private final TokenProvider tokenProvider;
    private final UsersRepository usersRepository;
    private final TasksService tasksService;
    TasksDto tasksDTO;

    public UserUtil(UsersRepository usersRepository, TokenProvider tokenProvider, TasksService tasksService) {
        this.usersRepository = usersRepository;
        this.tokenProvider = tokenProvider;
        this.tasksService = tasksService;
    }


    public Users tokenProcess(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        // Token'dan email çıkar
        String email = tokenProvider.getEmailFromToken(token);

        // Email'e göre kullanıcıyı bul
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    


}

