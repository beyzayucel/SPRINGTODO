package com.haratres.todo.dto;

import java.util.List;

public class AdminGetUsersDto {
    private String firstName;
    private String lastName;
    private String email;
    private List<TasksDto> tasks;
    private String tel;
    private List<ImageDto> userImages;


    public AdminGetUsersDto(String email, String firstName, String lastName, List<TasksDto> tasks, String tel, List<ImageDto> userImages) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tasks = tasks;
        this.tel = tel;
        this.userImages = userImages;
    }

    public List<ImageDto> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<ImageDto> userImages) {
        this.userImages = userImages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<TasksDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksDto> tasks) {
        this.tasks = tasks;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
