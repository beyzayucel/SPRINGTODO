package com.haratres.todo.services.task;

import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;

import java.util.List;

public interface TasksService {

    public Tasks createTasks(TasksDto tasksDTO, Users user);
    public List<Tasks> getTasksForUser(Users user);
    public Tasks updateTasks(int id, TasksDto tasksDTO,Users users);
    public Boolean deleteTasks(int id,Users users);
    public Tasks getTasksTitle(String title,Users users);
//    public List<TasksDto> getTasksStatus(String status);
    public List<Tasks> sortForTitle(Users users);
//    public TasksDto updateStatus(int id, TasksStatus newStatus);
//    public List<TasksDto> getByDate(LocalDate date);

    }
