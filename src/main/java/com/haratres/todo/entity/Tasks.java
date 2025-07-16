package com.haratres.todo.entity;

import com.haratres.todo.enums.TasksStatus;
import jakarta.persistence.*;


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
    private String createdDate;


    @Column(name = "important")
    private String important;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TasksStatus status;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Tasks() {
    }

    public Tasks(String createdDate, String description,String important, TasksStatus status, String title, Users users) {
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
