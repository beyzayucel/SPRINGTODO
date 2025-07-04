package com.haratres.todo.services.task;

import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;

import java.util.List;

public interface TasksService {

    Tasks createTasks(TasksDto tasksDTO, Users user);
    List<Tasks> getTasksForUser(Users user);
    Tasks updateTasks(int id, TasksDto tasksDTO,Users users);
    Boolean deleteTasks(int id,Users users);
    Tasks getTasksTitle(String title,Users users);
    List<Tasks> getStatus(TasksStatus tasksStatus,Users users);
    List<Tasks> sortForDate(Users users);


    }
