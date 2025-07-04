package com.haratres.todo.validators;

import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsersValidators implements Validator {

    private static final String emailRegex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String telRegex="[0-9]{10}$";
    private static final String nameRegex="^[a-zA-ZçÇğĞıİöÖşŞüÜ]{2,}$";

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersDto usersDto=(UsersDto) target;


        if (StringUtils.isBlank(usersDto.getEmail())) {
            errors.reject("email", "The e-mail field cannot be left blank.");
        } else {
            if (!usersDto.getEmail().matches(emailRegex)) {
                errors.reject("email", "You entered the email in the wrong format.");
            } else if (usersRepository.findByEmail(usersDto.getEmail()).isPresent()) {
                errors.reject("email", "Farklı mail giriniz");
            }
        }
        if(StringUtils.isBlank(usersDto.getPassword())){
            errors.reject("password","The password field cannot be blank.");
        }

        if(!usersDto.getTel().matches(telRegex)){
            errors.reject("tel","You entered the phone number in the wrong format.");
        }
        if(!usersDto.getFirstName().matches(nameRegex)){
            errors.reject("firstName","You cannot use any other characters than letters in the name.");
        }
        if(!usersDto.getLastName().matches(nameRegex)){
            errors.reject("lastName","You cannot use any other characters than letters in my surname.");
        }


    }
}
