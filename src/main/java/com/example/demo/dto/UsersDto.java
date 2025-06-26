package com.example.demo.dto;

import com.example.demo.entity.Roles;
import com.example.demo.entity.Tasks;

import java.util.List;
import java.util.Set;


public class UsersDto {

    private String firstName;
    private String lastName;
    private String email;
    private List<TasksDto> tasks;
    private String password;
    private String tel;


    public UsersDto(String password,String email, String firstName, String lastName,List<TasksDto> tasks,String tel) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tasks = tasks;
        this.password=password;
        this.tel=tel;
    }

    public UsersDto() {
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
