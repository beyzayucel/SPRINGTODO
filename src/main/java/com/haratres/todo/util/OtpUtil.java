package com.haratres.todo.util;

import java.security.SecureRandom;

public class OtpUtil {
    private static final SecureRandom random=new SecureRandom();

    public static String createOtp(){
        int number=random.nextInt(900000)+100000;
        return String.valueOf(number);

    }

}
