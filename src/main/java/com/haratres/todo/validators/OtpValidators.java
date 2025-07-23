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
public class OtpValidators implements Validator {

    @Autowired
    UsersRepository usersRepository;

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return ForgottenPasswordOtpDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ForgottenPasswordOtpDto forgottenPasswordOtpDto=(ForgottenPasswordOtpDto) target;
        Optional<Users> user = usersRepository.findByEmail(forgottenPasswordOtpDto.getEmail());

        if (StringUtils.isBlank(forgottenPasswordOtpDto.getEmail())) {
            errors.rejectValue("email","email.is.blank.error", "The e-mail field cannot be left blank.");
        }
        if (!forgottenPasswordOtpDto.getEmail().matches(emailRegex)) {
            errors.rejectValue("email","email.is.wrong.format.error","You entered the email in the wrong format.");
        }
        if (user.isEmpty()) {
            errors.rejectValue("email","user.is.not.find.error", "User didn't find");
        }
        if (StringUtils.isBlank(forgottenPasswordOtpDto.getOtp())) {
            errors.rejectValue("otp", "otp.blank.error", "Otp cannot be blank");
        }
    }
}

