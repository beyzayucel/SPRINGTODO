package com.haratres.todo.services.task;

import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasksService {

    public Tasks createTasks(TasksDto tasksDTO, Users user);
    public List<Tasks> getTasksForUser(Users user);
    public Tasks updateTasks(int id, TasksDto tasksDTO,Users users);
    public Boolean deleteTasks(int id,Users users);
    public Tasks getTasksTitle(String title,Users users);


    @Query("select title,created_date,important,description from tasks order by title asc")
    public List<Tasks> sortForTitle(Users users);


    }
