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

    private static final String emailRegex = "^[\\p{L}0-9._%+-]+@[\\p{L}0-9.-]+\\.[\\p{L}]{2,}$";
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
            errors.rejectValue("email", "email.is.blank.error","Email is blank.");
        } else {
            if (!(usersDto.getEmail().matches(emailRegex))) {
                errors.rejectValue("email","email.is.wrong.format.error", "You entered the email in the wrong format.");
            } if (usersRepository.findByEmail(usersDto.getEmail()).isPresent()) {
                errors.rejectValue("email","email.is.register.system.error", "Enter different email");
            }
        }
        if(StringUtils.isBlank(usersDto.getPassword())){
            errors.rejectValue("password","password.is.empty.error","The password field cannot be blank.");
        }
        if(!usersDto.getTel().matches(telRegex)){
            errors.rejectValue("tel","tel.is.wrong.format.error","You entered the phone number in the wrong format.");
        }
        if(!usersDto.getFirstName().matches(nameRegex)){
            errors.rejectValue("firstName","firstName.is.wrong.format.error","You cannot use any other characters than letters in the name.");
        }
        if(!usersDto.getLastName().matches(nameRegex)){
            errors.rejectValue("lastName","lastName.is.wrong.format.error","You cannot use any other characters than letters in my surname.");
        }
    }
}
