package com.haratres.todo.controller;

import com.haratres.todo.config.Validationhandler;
import com.haratres.todo.dto.TasksDto;
import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;
import com.haratres.todo.services.task.TasksService;
import com.haratres.todo.util.UserUtil;
import com.haratres.todo.validators.TasksValidators;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
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
    Validationhandler validationhandler;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> createTasks(@RequestBody TasksDto tasksDTO, HttpServletRequest request, Errors errors) {

        tasksValidators.validate(tasksDTO,errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationhandler.validate(errors));
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
        return ResponseEntity.ok("Successfully Updated");
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable int id){

        Users users=userUtil.tokenProcess(request);
        tasksService.deleteTasks(id,users);
        return ResponseEntity.ok("Successfully Deleted");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/title/{title}")
    public TasksDto getTitle(@PathVariable String title,HttpServletRequest request){

        Users users=userUtil.tokenProcess(request);

        Tasks tasksTitle=tasksService.getTasksTitle(title,users);
        return modelMapper.map(tasksTitle,TasksDto.class);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/status/{status}")
    public List<TasksDto> getStatus(@PathVariable TasksStatus status,HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);

        List<Tasks> tasksTitle=tasksService.getStatus(status,users);
        return tasksTitle.stream().map(task -> modelMapper.map(task,TasksDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sortDate")
    public List<TasksDto> getSortDate(HttpServletRequest request){
        Users users=userUtil.tokenProcess(request);

        List<Tasks> tasks=tasksService.sortForDate(users);

        return tasks.stream().map(task -> modelMapper.map(task,TasksDto.class)).collect(Collectors.toList());


    }

}
