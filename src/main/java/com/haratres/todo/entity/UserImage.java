package com.haratres.todo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imageUrl;

    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    public UserImage(Date date, String imageUrl, Users users) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.users = users;
    }

    public UserImage() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
