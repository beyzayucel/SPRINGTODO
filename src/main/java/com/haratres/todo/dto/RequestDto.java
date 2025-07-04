package com.haratres.todo.dto;

public class RequestDto {
    private String newPassword;
    private String otp;
    private String email;

    public RequestDto(String newPassword, String otp) {
        this.newPassword = newPassword;
        this.otp = otp;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
