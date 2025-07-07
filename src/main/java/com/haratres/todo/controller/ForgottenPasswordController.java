package com.haratres.todo.controller;

import com.haratres.todo.config.Validationhandler;
import com.haratres.todo.dto.ForgottenPasswordOtpDto;
import com.haratres.todo.entity.Otp;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.OtpRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.services.SendEmailService;
import com.haratres.todo.util.OtpUtil;
import com.haratres.todo.validators.EmailValidators;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgottenPasswordController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OtpUtil otpUtil;

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    EmailValidators emailValidators;

    @Autowired
    Validationhandler validationhandler;

    @PermitAll
    @PostMapping("/forgotten-password-send-otp")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto, Errors errors) {

        emailValidators.validate(forgottenPasswordOtpDto,errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationhandler.validate(errors));
        }

        Users users=usersRepository.findByEmail(forgottenPasswordOtpDto.getEmail()).get();


        int userId=users.getId();
        String userıd=String.valueOf(userId);
        String otpCode=otpUtil.createOtp(users.getEmail(),userıd);
        sendEmailService.sendEmail(users,otpCode);

        return ResponseEntity.ok().build();

    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/forgotten-password-verfiy")
    public ResponseEntity<?> enterEmailandOtp(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto,Errors errors) {
        emailValidators.validate(forgottenPasswordOtpDto,errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationhandler.validate(errors));
        }

        String otpCode = forgottenPasswordOtpDto.getOtp();
        Users user = usersRepository.findByEmail(forgottenPasswordOtpDto.getEmail()).get();

        Otp otp = otpRepository.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());

        if (otpCode.equals(otp.getOtp()) && forgottenPasswordOtpDto.getEmail().equals(user.getEmail())) {
            otp.setVerified(true);
            otpRepository.save(otp);
            return ResponseEntity.ok(true);
        }
        otp.setVerified(false);
        otpRepository.save(otp);
        return ResponseEntity.ok(false);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/forgotten-password-reset")
    public ResponseEntity<?> enterNewPassword(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto,Errors errors) {

        emailValidators.validate(forgottenPasswordOtpDto,errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationhandler.validate(errors));
        }


        String newPassword = forgottenPasswordOtpDto.getNewPassword();
        String newPasswordAgain = forgottenPasswordOtpDto.getNewPasswordAgain();

        Otp otp = otpRepository.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());
        if (otp.isVerified() == true && newPassword.equals(newPasswordAgain)) {
            Users users = usersRepository.findByEmail(forgottenPasswordOtpDto.getEmail()).get();
            users.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(users);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
