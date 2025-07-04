package com.haratres.todo.controller;

import com.haratres.todo.dto.EmailDto;
import com.haratres.todo.dto.RequestDto;
import com.haratres.todo.entity.Otp;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.OtpRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.util.OtpUtil;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PermitAll
    @PostMapping("/forget")
    public ResponseEntity<?> forgetPassword(@RequestBody EmailDto emailDto) {
        String email = emailDto.getEmail();
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User didn't find"));
        String otp = OtpUtil.createOtp();

        otpRepository.save(new Otp(email, user, otp));


        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setText("Otp sıfırlama kodu: " + otp);
        mailSender.send(simpleMailMessage);

        return ResponseEntity.ok("Password reset link sent.");

    }

    @PermitAll
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody RequestDto request) {
        String email = request.getEmail();
        String newPassword = request.getNewPassword();
        String otp=request.getOtp();
        Users user=usersRepository.findByEmail(email).get();

        if(!request.getEmail().equals(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User didn't find.");
        }

        Otp otp1=otpRepository.findTopByEmailOrderByIdDesc(email);

        if(otp.equals(otp1.getOtp())){
            Users users=usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User didn't find"));

            users.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(users);
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect upt!");


    }
}
