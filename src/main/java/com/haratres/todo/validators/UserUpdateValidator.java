package com.haratres.todo.validators;

import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Autowired
    UsersRepository usersRepository;

    private static final String emailRegex = "^[\\p{L}0-9._%+-]+@[\\p{L}0-9.-]+\\.[\\p{L}]{2,}$";
    private static final String telRegex = "[0-9]{10}$";
    private static final String nameRegex = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]{2,}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersDto usersDto = (UsersDto) target;

        if (StringUtils.isNotBlank(usersDto.getEmail())) {
            if (!(usersDto.getEmail().matches(emailRegex))) {
                errors.rejectValue("email", "email.is.wrong.format.error", "You entered the email in the wrong format.");
            }
            if(usersRepository.findByEmail(usersDto.getEmail()).isPresent()){
                errors.rejectValue("email", "email.is.register.system.error", "Email is register system.");
            }
        }
        if (StringUtils.isNotBlank(usersDto.getTel())) {
            if (!usersDto.getTel().matches(telRegex)) {
                errors.rejectValue("tel", "tel.is.wrong.format.error", "You entered the phone number in the wrong format.");
            }
        }
        if (StringUtils.isNotBlank(usersDto.getFirstName())) {
            if (!usersDto.getFirstName().matches(nameRegex)) {
                errors.rejectValue("firstName", "firstName.is.wrong.format.error", "You cannot use any other characters than letters in the name.");
            }
        }
        if (StringUtils.isNotBlank(usersDto.getLastName())) {
            if (!usersDto.getLastName().matches(nameRegex)) {
                errors.rejectValue("lastName", "lastName.is.wrong.format.error", "You cannot use any other characters than letters in my surname.");
            }
        }
    }
}

