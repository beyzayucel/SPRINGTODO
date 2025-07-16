package com.haratres.todo.validators;
import com.haratres.todo.dto.ForgottenPasswordOtpDto;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PasswordValidators implements Validator {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ForgottenPasswordOtpDto forgottenPasswordOtpDto = (ForgottenPasswordOtpDto) target;
        Optional<Users> user = usersRepository.findByEmail(forgottenPasswordOtpDto.getEmail());

        if (StringUtils.isBlank(forgottenPasswordOtpDto.getEmail())) {
            errors.rejectValue("email", "email.is.blank.error", "Email field cannot be empty");
        }
        else if (user.isEmpty()) {
            errors.rejectValue("email","user.is.not.find.error", "User didn't find");
        }

        if (StringUtils.isBlank(forgottenPasswordOtpDto.getNewPassword()) || StringUtils.isBlank(forgottenPasswordOtpDto.getNewPasswordAgain())) {
            errors.rejectValue("newPassword", "password.empty", "Passwords cannot be empty");
        }

        if (!forgottenPasswordOtpDto.getNewPassword().equals(forgottenPasswordOtpDto.getNewPasswordAgain())) {
            errors.rejectValue("newPasswordAgain", "passwords.not.match", "Passwords do not match");
        }
    }
}


