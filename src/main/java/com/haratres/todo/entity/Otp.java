package com.haratres.todo.entity;

import jakarta.persistence.*;

@Table(name = "password_reset_token")
@Entity
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "email")
    private String email;

    @Column(name = "new_password")
    private String newPassword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Otp(String email,Users users,String otp) {
        this.email=email;
        this.users=users;
        this.otp=otp;
    }

    public Otp(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Users getUsers() {
        return users;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
