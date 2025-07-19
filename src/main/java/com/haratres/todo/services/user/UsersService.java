package com.haratres.todo.services.user;
import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Users;


public interface UsersService {
    Users registerUser(UsersDto users);
    Users updateUser(Users newUser, String email);

    }
