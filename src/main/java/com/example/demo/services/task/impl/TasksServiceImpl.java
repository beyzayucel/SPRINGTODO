package com.example.demo.services.task.impl;

import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.enums.TasksStatus;
import com.example.demo.repository.TasksRepository;
import com.example.demo.services.task.TasksService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.sort;

@Transactional
@Service
public class TasksServiceImpl implements TasksService {

    TasksRepository tasksRepository;
    ModelMapper modelMapper;


    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository,ModelMapper modelMapper) {
        this.tasksRepository = tasksRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public Tasks createTasks(TasksDto tasksDTO, Users users) {
        Tasks task = new Tasks();
        task.setTitle(tasksDTO.getTitle());
        task.setDescription(tasksDTO.getDescription());
        task.setStatus(tasksDTO.getStatus());
        task.setImportant(tasksDTO.getImportant());
        task.setCreatedDate(tasksDTO.getCreatedDate());
        List<Users> userList = new ArrayList<>();
        userList.add(users);
        task.setUsers(userList);

        task.addUser(users); // bu, Tasks içinde user listesine ekler
        users.addTasks(task); // bu da Users içinde task listesine ekler

        task = tasksRepository.save(task);

        return task;
    }



    public List<Tasks> getTasksForUser(Users users) {
        List<Tasks> tasks = users.getTasks(); // sadece bu kullanıcıya ait görevler
        return tasks.stream().collect(Collectors.toList());
    }


    public Tasks updateTasks(int id, TasksDto tasksDTO, Users user) {
        List<Tasks> tasksList = user.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);

        if (targetTask.equals(null)) {
            targetTask.setTitle(tasksDTO.getTitle());
            targetTask.setDescription(tasksDTO.getDescription());
            targetTask.setImportant(tasksDTO.getImportant());
            targetTask.setCreatedDate(tasksDTO.getCreatedDate());

            return tasksRepository.save(targetTask);
        }
        return null;
    }

    public Boolean deleteTasks(int id,Users user) {

        List<Tasks> tasksList = user.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);

        if(targetTask.equals(null)){
            int taskId=targetTask.getId();
            tasksRepository.deleteById(taskId);
            return true;
        }
        return null;
    }

    public Tasks getTasksTitle(String title, Users users) {
        List<Tasks> tasksList = users.getTasks();

        Tasks targetTask = tasksList.stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (targetTask.equals(null)) {
            throw new RuntimeException(title+" bulunamadı.");
        }

        return targetTask;
    }

    public List<Tasks> sortForTitle(Users users){

        List<Tasks> tasksList = users.getTasks();

        tasksList.sort(Comparator.comparing(tasks -> tasks.getTitle().toLowerCase()));


        return tasksList;
    }



    //    public List<TasksDto> getTasksStatus(String status) {
//        List<Tasks> tasksList = tasksRepository.findByTitle(status);
//        return tasksList.stream().map(task -> modelMapper.map(task, TasksDto.class)).collect(Collectors.toList());
//    }
//
//
//    public TasksDto updateStatus(int id, TasksStatus newStatus){
//        Optional<Tasks> optionalTasks=tasksRepository.findById(id);
//        optionalTasks.get().setStatus(newStatus);
//        Tasks tasks=tasksRepository.save(optionalTasks.get());
//        return modelMapper.map(tasks, TasksDto.class);
//    }
//
//    public List<TasksDto> getByDate(LocalDate date){
//        List<Tasks> tasksList=tasksRepository.findAll();
//        List<TasksDto> tasksDTO=tasksList.stream().sorted(Comparator.comparing(tasks -> tasks.getCreatedDate().isAfter(date))).map(tasks -> modelMapper.map(tasks, TasksDto.class)).collect(Collectors.toList());
//        return tasksDTO;
//    }

}
