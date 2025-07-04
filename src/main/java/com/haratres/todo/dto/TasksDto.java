package com.haratres.todo.dto;


import com.haratres.todo.entity.Tasks;
import com.haratres.todo.enums.TasksStatus;

import java.time.LocalDate;



public class TasksDto {
    private String title;
    private String createdDate;
    private String important;
    private TasksStatus status;
    private String description;
    private int id;

    public TasksDto(String createdDate, String description, String important, TasksStatus status, String title) {
        this.createdDate = createdDate;
        this.description = description;
        this.important = important;
        this.status = status;
        this.title = title;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
