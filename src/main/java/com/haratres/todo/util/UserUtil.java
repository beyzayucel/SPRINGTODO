package com.haratres.todo.util;

import com.haratres.todo.config.TokenProvider;
import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.services.task.TasksService;
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

