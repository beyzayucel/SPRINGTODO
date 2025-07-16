package com.haratres.todo.services.task.impl;

import com.haratres.todo.controller.BaseController;
import com.haratres.todo.dto.MotivationDto;
import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.dto.TasksWithMotivationDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;
import com.haratres.todo.repository.TasksRepository;
import com.haratres.todo.services.motivation.MotivationService;
import com.haratres.todo.services.task.TasksService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
public class TasksServiceImpl extends BaseController implements TasksService {

    @Autowired
    TasksRepository tasksRepository;

    @Autowired
    MotivationService motivationService;

    @Override
    public Tasks createTasks(TasksDto tasksDTO, Users user) {
        Tasks task = new Tasks();
        task.setTitle(tasksDTO.getTitle());
        task.setDescription(tasksDTO.getDescription());
        task.setStatus(TasksStatus.CREATED);
        task.setImportant(tasksDTO.getImportant());
        task.setCreatedDate(tasksDTO.getCreatedDate());
        task.setUsers(user);
        task = tasksRepository.save(task);
        return task;
    }

    public TasksWithMotivationDto getTasksForUser(Users users) {
        List<Tasks> tasks = users.getTasks();
        List<TasksDto> tasksDto = tasks.stream().map(task -> modelMapper().map(task, TasksDto.class)).collect(Collectors.toList());
        MotivationDto motivationDto = motivationService.getMotivation();
        TasksWithMotivationDto tasksMotivationDto = new TasksWithMotivationDto();
        tasksMotivationDto.setMotivationDto(motivationDto);
        tasksMotivationDto.setTasks(tasksDto);
        return tasksMotivationDto;
    }

    public Tasks updateTasks(int id, TasksDto newTask, Users user) {
        Tasks updateTask = tasksRepository.getById(id);
        if (!updateTask.equals(null)) {
            if (updateTask.getStatus().equals(TasksStatus.CREATED)) {
                updateTask.setStatus(TasksStatus.IN_PROGRESS);
            }
            updateTask.setTitle(newTask.getTitle());
            updateTask.setDescription(newTask.getDescription());
            updateTask.setImportant(newTask.getImportant());
            updateTask.setCreatedDate(newTask.getCreatedDate());
            return tasksRepository.save(updateTask);
        }
        return null;
    }

    public Boolean deleteTasks(int id, Users user) {
        List<Tasks> tasksList = user.getTasks();
        Tasks deleteTask = tasksList.stream().filter(task -> task.getId() == id).findFirst().get();
        if (deleteTask == null) {
            return false;
        }
        tasksRepository.delete(deleteTask);
        return true;
    }

    public Tasks getTasksTitle(String title, Users users) {
        List<Tasks> tasksList = users.getTasks();
        Tasks getTask = tasksList.stream().filter(task -> task.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
        if (getTask.equals(null)) {
            throw new RuntimeException(title + " not found.");
        }
        return getTask;
    }

    public List<Tasks> getStatus(TasksStatus tasksStatus, Users users) {
        return tasksRepository.getStatus(tasksStatus, users);
    }

    public List<Tasks> sortForDate(Users users) {
        return tasksRepository.sortByDate(users);
    }

    public Tasks getTaskById(int id, Users users) {
        List<Tasks> tasksList = users.getTasks();
        Optional<Tasks> getOneTasks = tasksList.stream().filter(task -> task.getId() == id).findFirst();
        if (getOneTasks.isEmpty()) {
            throw new RuntimeException(id + " not found.");
        }
        return getOneTasks.get();
    }
}