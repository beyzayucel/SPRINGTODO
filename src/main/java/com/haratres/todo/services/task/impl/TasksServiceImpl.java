package com.haratres.todo.services.task.impl;

import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;
import com.haratres.todo.repository.TasksRepository;
import com.haratres.todo.services.task.TasksService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    TasksRepository tasksRepository;


    @Override
    public Tasks createTasks(TasksDto tasksDTO, Users users) {
        Tasks task = new Tasks();
        task.setTitle(tasksDTO.getTitle());
        task.setDescription(tasksDTO.getDescription());
        task.setStatus(TasksStatus.CREATED);
        task.setImportant(tasksDTO.getImportant());
        task.setCreatedDate(tasksDTO.getCreatedDate());
        task.setUsers(users);

        task = tasksRepository.save(task);

        return task;
    }



    public List<Tasks> getTasksForUser(Users users) {
        List<Tasks> tasks = users.getTasks();
        return tasks.stream().collect(Collectors.toList());
    }


    public Tasks updateTasks(int id, TasksDto newTask, Users user) {
        List<Tasks> tasksList = user.getTasks();

        Tasks updateTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);


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

        Tasks deleteTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);

        if (deleteTask.equals(null)) {
            return false;
        }
        tasksRepository.deleteById(id);
        return true;
    }

    public Tasks getTasksTitle(String title, Users users) {
        List<Tasks> tasksList = users.getTasks();

        Tasks getTask = tasksList.stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

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

}
