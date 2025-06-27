package com.haratres.todo.entity;

import com.haratres.todo.enums.TasksStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;


    @Column(name = "created_date")
    private LocalDate createdDate;


    @Column(name = "important")
    private String important;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TasksStatus status;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},mappedBy ="tasks")
    private List<Users> users;

    public void addUser(Users user) {
        if(users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }


    public Tasks(TasksStatus status) {
        this.status = TasksStatus.CREATED;
    }

    public Tasks() {
    }

    public Tasks(LocalDate createdDate, String description, String important, TasksStatus status, String title, List<Users> users) {
        this.createdDate = createdDate;
        this.description = description;
        this.important = important;
        this.status = status;
        this.title = title;
        this.users = users;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImportant() {
        return important;
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

    public TasksStatus getStatus() {
        return status;
    }

    public void setStatus(TasksStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}


/*
public void addUsers(Users theUsers){
        if(users==null){
            users=new ArrayList<>();
        }
        users.add(theUsers);
        theUsers.addTasks(this);
    }
 */