package com.haratres.todo.services.email;
import com.haratres.todo.dto.ForgottenPasswordOtpDto;
import com.haratres.todo.entity.Otp;
import com.haratres.todo.entity.Users;
import com.haratres.todo.exception.InvalidOtpException;
import com.haratres.todo.repository.OtpRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OtpUtil otpUtil;

    @Autowired
    JavaMailSender javaMailSender;

    public ResponseEntity<?> forgetPasswordService(ForgottenPasswordOtpDto dto) {
        Users user = usersRepository.findByEmail(dto.getEmail()).orElseThrow();
        String otpCode = otpUtil.createOtp(user.getEmail(), String.valueOf(user.getId()));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setText("Otp sıfırlama kodu: " + otpCode);
        javaMailSender.send(simpleMailMessage);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> verifyOtp(ForgottenPasswordOtpDto dto) {
        Otp otp = otpRepository.findTopByEmailOrderByIdDesc(dto.getEmail());
        if (otp == null || !dto.getOtp().equals(otp.getOtp())) {
            if (otp != null) {
                otp.setVerified(false);
                otpRepository.save(otp);
            }
            throw new InvalidOtpException("OTP code is incorrect");
        }
        otp.setVerified(true);
        otpRepository.save(otp);
        return ResponseEntity.ok().build();
    }

    public void resetPassword(ForgottenPasswordOtpDto dto) {
        Users user = usersRepository.findByEmail(dto.getEmail()).orElseThrow();
        Otp otp = otpRepository.findTopByEmailOrderByIdDesc(dto.getEmail());
        if (otp == null || !otp.isVerified()) {
            throw new RuntimeException("OTP not valid");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        usersRepository.save(user);
        otpRepository.delete(otp);
    }
}