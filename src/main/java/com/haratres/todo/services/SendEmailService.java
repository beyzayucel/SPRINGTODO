package com.haratres.todo.services;

import com.haratres.todo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(Users user,String otpCode) {
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setText("Otp sıfırlama kodu: " + otpCode);
        mailSender.send(simpleMailMessage);
    }
}
