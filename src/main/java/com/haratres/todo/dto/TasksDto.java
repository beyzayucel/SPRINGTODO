package com.haratres.todo.dto;


import com.haratres.todo.entity.Tasks;
import com.haratres.todo.enums.TasksStatus;

import java.time.LocalDate;



public class TasksDto {
    private String title;
    private LocalDate createdDate;
    private String important;
    private TasksStatus status;
    private String description;
    private String userEmail;
    private int id;


    public TasksDto(TasksStatus status) {
        this.status = TasksStatus.CREATED;
    }

    public TasksDto(LocalDate createdDate, String description, String important, TasksStatus status, String title,String userEmail) {
        this.createdDate = createdDate;
        this.description = description;
        this.important = important;
        this.status = status;
        this.title = title;
        this.userEmail=userEmail;
    }

    private TasksDto convertToDto(Tasks task) {
        TasksDto dto = new TasksDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedDate(task.getCreatedDate());
        dto.setImportant(task.getImportant());
        return dto;
    }


    public TasksDto() {
    }


    public TasksStatus getStatus() {
        return status;
    }

    public void setStatus(TasksStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getTitle() {
        return title;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
