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


    public Tasks updateTasks(int id, TasksDto tasksDTO, Users user) {
        List<Tasks> tasksList = user.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);


        if (!targetTask.equals(null)) {
            if (targetTask.getStatus().equals(TasksStatus.CREATED)) {
                targetTask.setStatus(TasksStatus.IN_PROGRESS);
            }
            targetTask.setTitle(tasksDTO.getTitle());
            targetTask.setDescription(tasksDTO.getDescription());
            targetTask.setImportant(tasksDTO.getImportant());
            targetTask.setCreatedDate(tasksDTO.getCreatedDate());

            return tasksRepository.save(targetTask);
        }
        return null;
    }

    public Boolean deleteTasks(int id, Users user) {

        List<Tasks> tasksList = user.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);

        if (targetTask.equals(null)) {
            return false;
        }
        tasksRepository.deleteById(id);
        return true;
    }

    public Tasks getTasksTitle(String title, Users users) {
        List<Tasks> tasksList = users.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (targetTask.equals(null)) {
            throw new RuntimeException(title + " not found.");
        }

        return targetTask;
    }

    public List<Tasks> getStatus(TasksStatus tasksStatus, Users users) {
        return tasksRepository.getStatus(tasksStatus, users);
    }

    public List<Tasks> sortForDate(Users users) {
        return tasksRepository.sortByDate(users);
    }

}
