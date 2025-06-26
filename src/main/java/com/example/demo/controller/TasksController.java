package com.example.demo.controller;


import com.example.demo.config.TokenProvider;
import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.services.task.TasksService;
import com.example.demo.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TasksController {

    private final ModelMapper modelMapper;
    private final UserUtil userUtil;
    private final TasksService tasksService;

    @Autowired
    public TasksController(ModelMapper modelMapper, UserUtil userUtil, TasksService tasksService) {
        this.modelMapper = modelMapper;
        this.userUtil = userUtil;
        this.tasksService = tasksService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public TasksDto createTasks(@RequestBody TasksDto tasksDTO, HttpServletRequest request) {

        Users users=userUtil.tokenProcess(request);

        Tasks task1=tasksService.createTasks(tasksDTO, users);

        return modelMapper.map(task1,TasksDto.class);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<TasksDto> list(HttpServletRequest request){

        Users users=userUtil.tokenProcess(request);
        List<Tasks> tasksList=tasksService.getTasksForUser(users);

        return tasksList.stream().map(tasks -> modelMapper.map(tasks,TasksDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public void update(@RequestBody TasksDto tasksDTO,HttpServletRequest request,@PathVariable int id){

        Users users=userUtil.tokenProcess(request);
        tasksService.updateTasks(id,tasksDTO,users);

    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public void delete(HttpServletRequest request,@PathVariable int id){

        Users users=userUtil.tokenProcess(request);
        tasksService.deleteTasks(id,users);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/title/{title}")
    public TasksDto getTitle(@PathVariable String title,HttpServletRequest request){

        Users users=userUtil.tokenProcess(request);

        Tasks tasksTitle=tasksService.getTasksTitle(title,users);
        return modelMapper.map(tasksTitle,TasksDto.class);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sort")
    public List<TasksDto> getSort(HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);

        List<Tasks> tasks=tasksService.sortForTitle(users);

        return tasks.stream().map(tasks1 -> modelMapper.map(tasks,TasksDto.class)).collect(Collectors.toList());
    }
}

//validations oluştur
