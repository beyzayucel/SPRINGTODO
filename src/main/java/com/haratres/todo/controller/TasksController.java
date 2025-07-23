package com.haratres.todo.controller;

import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.dto.TasksWithMotivationDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;
import com.haratres.todo.services.task.TasksService;
import com.haratres.todo.util.UserUtil;
import com.haratres.todo.validators.TaskUpdateValidator;
import com.haratres.todo.validators.TasksValidators;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TasksController extends BaseController{

    @Autowired
    UserUtil userUtil;

    @Autowired
    TasksService tasksService;

    @Autowired
    TasksValidators tasksValidators;

    @Autowired
    TaskUpdateValidator taskUpdateValidator;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<TasksDto> createTasks(@RequestBody TasksDto tasksDTO, HttpServletRequest request) {
        validate(tasksDTO,"task",tasksValidators);
        Users users=userUtil.tokenProcess(request);
        Tasks task=tasksService.createTasks(tasksDTO, users);
        TasksDto tasksDto=modelMapper().map(task,TasksDto.class);
        return  ResponseEntity.ok(tasksDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public TasksWithMotivationDto list(HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);
        return tasksService.getTasksForUser(users);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@RequestBody TasksDto tasksDTO,HttpServletRequest request,@PathVariable int id){
        validate(tasksDTO,"task",taskUpdateValidator);
        Users users=userUtil.tokenProcess(request);
        tasksService.updateTasks(id,tasksDTO,users);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable int id){
        Users users=userUtil.tokenProcess(request);
        Boolean deleteTasks=tasksService.deleteTasks(id,users);
        if(deleteTasks){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/title/{title}")
    public TasksDto getTaskByTitle(@PathVariable String title,HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);
        Tasks tasksTitle=tasksService.getTasksTitle(title,users);
        return modelMapper().map(tasksTitle,TasksDto.class);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/status/{status}")
    public List<TasksDto> getTasksByStatus(@PathVariable TasksStatus status,HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);
        List<Tasks> tasksTitle=tasksService.getStatus(status,users);
        return tasksTitle.stream().map(task -> modelMapper().map(task,TasksDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sortDate")
    public List<TasksDto> getSortDate(HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);
        List<Tasks> tasks=tasksService.sortForDate(users);
        return tasks.stream().map(task -> modelMapper().map(task,TasksDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/{id}")
    public TasksDto getTaskById(@PathVariable int id,HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);
        Tasks tasksTitle=tasksService.getTaskById(id,users);
        return modelMapper().map(tasksTitle,TasksDto.class);
    }
}
