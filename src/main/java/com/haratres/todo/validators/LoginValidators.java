package com.haratres.todo.validators;
import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.entity.Users;
import com.haratres.todo.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class LoginValidators implements Validator {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersDto usersDto = (UsersDto) target;

        if (StringUtils.isBlank(usersDto.getEmail())) {
            errors.rejectValue("email", "email.is.blank.error","Email is blank.");
        }
        Optional<Users> user = usersRepository.findByEmail(usersDto.getEmail());

        if (user.isEmpty()) {
            errors.rejectValue("email","user.is.not.find.error", "User didn't find");
        } else {
            if (!passwordEncoder.matches(usersDto.getPassword(), user.get().getPassword())) {
                errors.rejectValue("password","password.is.wrong.error", "Wrong password");
            }
        }
    }
}

