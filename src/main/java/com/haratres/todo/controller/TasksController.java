package com.haratres.todo.controller;


import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.services.task.TasksService;
import com.haratres.todo.util.UserUtil;
import com.haratres.todo.validators.TasksValidators;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TasksController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserUtil userUtil;

    @Autowired
    TasksService tasksService;

    @Autowired
    TasksValidators tasksValidators;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> createTasks(@RequestBody TasksDto tasksDTO, HttpServletRequest request, Errors errors) {

        tasksValidators.validate(tasksDTO,errors);

        if (errors.hasErrors()) {
            List<Map<String, String>> errorList = errors.getAllErrors().stream()
                    .map(error -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("code", error.getCode());
                        map.put("message", error.getDefaultMessage());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorList);
        }


        Users users=userUtil.tokenProcess(request);

        Tasks task1=tasksService.createTasks(tasksDTO, users);
        TasksDto tasksDto=modelMapper.map(task1,TasksDto.class);

        return ResponseEntity.ok(tasksDto);
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
    public ResponseEntity update(@RequestBody TasksDto tasksDTO,HttpServletRequest request,@PathVariable int id){

        Users users=userUtil.tokenProcess(request);
        tasksService.updateTasks(id,tasksDTO,users);
        return ResponseEntity.ok("Başarıyla Güncellendi");
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable int id){

        Users users=userUtil.tokenProcess(request);
        tasksService.deleteTasks(id,users);
        return ResponseEntity.ok("Başarıyla Silindi");
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

        return tasks.stream().map(tasks1 -> modelMapper.map(tasks1,TasksDto.class)).collect(Collectors.toList());
    }
}
