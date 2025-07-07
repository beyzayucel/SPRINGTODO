package com.haratres.todo.validators;

import com.haratres.todo.dto.ForgottenPasswordOtpDto;
import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValidators implements Validator {

    @Autowired
    UsersRepository usersRepository;

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ForgottenPasswordOtpDto forgottenPasswordOtpDto = (ForgottenPasswordOtpDto) target;

        if (StringUtils.isBlank(forgottenPasswordOtpDto.getEmail())) {
            errors.reject("email", "The e-mail field cannot be left blank.");
        } else {
            if (!forgottenPasswordOtpDto.getEmail().matches(emailRegex)) {
                errors.reject("email", "You entered the email in the wrong format.");
            }

        }

    }


}

