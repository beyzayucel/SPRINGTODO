package com.haratres.todo.util;

import com.haratres.todo.entity.Otp;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpUtil {

    @Autowired
    OtpRepository otpRepository;

    private final SecureRandom random=new SecureRandom();

    public String createOtp(String email, String userId){

        int number=random.nextInt(900000)+100000;
        otpRepository.save(new Otp(email,userId,String.valueOf(number)));

        return String.valueOf(number);

    }

}
