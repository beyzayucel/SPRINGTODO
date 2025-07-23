package com.haratres.todo.dto;

import java.util.List;

public class TasksWithMotivationDto {
    private MotivationDto motivationDto;
    private List<TasksDto> tasks;

    public MotivationDto getMotivationDto() {
        return motivationDto;
    }

    public void setMotivationDto(MotivationDto motivationDto) {
        this.motivationDto = motivationDto;
    }

    public List<TasksDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksDto> tasks) {
        this.tasks = tasks;
    }
}
