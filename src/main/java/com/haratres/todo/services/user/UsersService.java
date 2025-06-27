package com.haratres.todo.services.user;
import com.haratres.todo.entity.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {
    public List<Users> allGetUsers();
    public Users getById(int id);
    public void delete(int id);
    public Users saveUsers(Users users);
    public List<Map<String, Object>> getAllUsersWithTasks();

}
