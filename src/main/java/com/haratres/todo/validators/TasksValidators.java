package com.haratres.todo.validators;

import com.haratres.todo.dto.TasksDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TasksValidators implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TasksDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TasksDto tasksDto=(TasksDto) target;
        String importantRegex="^[a-zA-ZçÇğĞıİöÖşŞüÜ ]+$";
        String dateRegex="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";


        if(tasksDto.getTitle()==null|| tasksDto.getTitle().trim().isEmpty()){
            errors.rejectValue("title","title.empty","Başlık alanı boş olamaz.");
        }
        if(tasksDto.getImportant()==null){
            errors.reject("important","Görevin önemi alanı boş olamaz.");
        }
        else if(!tasksDto.getImportant().matches(importantRegex)){
            errors.reject("important","Görevin önemi alanında yalnızca harf olabilir.");
        }
        if(!tasksDto.getCreatedDate().matches(dateRegex)){
            errors.reject("createdDate","Tarih alanı yanlış formatta girildi.");

        }


    }
}
