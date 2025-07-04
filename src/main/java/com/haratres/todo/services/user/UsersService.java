package com.haratres.todo.services.user;
import com.haratres.todo.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UsersService {
    public Users saveUsers(Users users);

}
