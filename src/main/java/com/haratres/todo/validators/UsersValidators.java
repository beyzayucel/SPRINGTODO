package com.haratres.todo.validators;

import com.haratres.todo.dto.UsersDto;
import com.haratres.todo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsersValidators implements Validator {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersDto usersDto=(UsersDto) target;
        String emailRegex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String telRegex="[0-9]{10}$";
        String nameRegex="^[a-zA-ZçÇğĞıİöÖşŞüÜ]{2,}$";


        if (usersDto.getEmail() == null || usersDto.getEmail().trim().isEmpty()) {
            errors.reject("email", "Mail alanı boş girilemez.");
        } else {
            if (!usersDto.getEmail().matches(emailRegex)) {
                errors.reject("email", "Maili yanlış formatta girdiniz.");
            } else if (usersRepository.findByEmail(usersDto.getEmail()).isPresent()) {
                errors.reject("email", "Farklı mail giriniz");
            }
        }
        if(usersDto.getPassword()==null||usersDto.getPassword().trim().isEmpty()){
            errors.reject("password","Şifre alanı boş olamaz.");
        }

        if(!usersDto.getTel().matches(telRegex)){
            errors.reject("tel","Telefonu yanlış formatta girdiniz.");
        }
        if(!usersDto.getFirstName().matches(nameRegex)){
            errors.reject("firstName","İsimde harften başka harakter kullanamzsınız.");
        }
        if(!usersDto.getLastName().matches(nameRegex)){
            errors.reject("lastName","Soyisimde harften başka harakter kullanamzsınız.");
        }


    }
}
