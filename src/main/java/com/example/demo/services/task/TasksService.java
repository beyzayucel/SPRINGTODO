package com.example.demo.services.task;

import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.enums.TasksStatus;

import java.time.LocalDate;
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
