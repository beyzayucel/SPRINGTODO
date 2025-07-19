package com.haratres.todo.controller;
import com.haratres.todo.dto.ForgottenPasswordOtpDto;
import com.haratres.todo.services.email.SendEmailService;
import com.haratres.todo.validators.EmailValidators;
import com.haratres.todo.validators.OtpValidators;
import com.haratres.todo.validators.PasswordValidators;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgottenPasswordController extends BaseController{

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    OtpValidators otpValidators;

    @Autowired
    PasswordValidators passwordValidators;

    @Autowired
    EmailValidators emailValidators;

    @PermitAll
    @PostMapping("/forgotten-password-send-otp")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        validate(forgottenPasswordOtpDto,"forgettenPassword",emailValidators);
        return sendEmailService.forgetPasswordService(forgottenPasswordOtpDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/forgotten-password-verify")
    public ResponseEntity<?> verifyPassword(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        validate(forgottenPasswordOtpDto,"forgettenPassword",otpValidators);
        return sendEmailService.verifyOtp(forgottenPasswordOtpDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/forgotten-password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        validate(forgottenPasswordOtpDto,"forgettenPassword",passwordValidators);
        sendEmailService.resetPassword(forgottenPasswordOtpDto);
        return ResponseEntity.ok().build();
    }
}