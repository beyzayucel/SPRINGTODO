package com.haratres.todo.dto;

import java.util.List;


public class UsersDto {

    private String firstName;
    private String lastName;
    private String email;
    private List<TasksDto> tasks;
    private String password;
    private String tel;
    private List<ImageDto> userImages;


    public UsersDto(String email, String firstName, List<ImageDto> userImages, String lastName, String password, List<TasksDto> tasks, String tel) {
        this.email = email;
        this.firstName = firstName;
        this.userImages = userImages;
        this.lastName = lastName;
        this.password = password;
        this.tasks = tasks;
        this.tel = tel;
    }

    public UsersDto() {
    }

    public List<ImageDto> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<ImageDto> userImages) {
        this.userImages = userImages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
}
