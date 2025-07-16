package com.haratres.todo.validators;
import com.haratres.todo.dto.TasksDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TasksValidators implements Validator {

    private static final String importantRegex="^[a-zA-ZçÇğĞıİöÖşŞüÜ ]+$";
    private static final String dateRegex="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    @Override
    public boolean supports(Class<?> clazz) {
        return TasksDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TasksDto tasksDto=(TasksDto) target;

        if(StringUtils.isBlank(tasksDto.getTitle())){
            errors.rejectValue("title","title.is.empty.error","The title field cannot be empty.");
        }
        else if(StringUtils.isBlank(tasksDto.getImportant())){
            errors.rejectValue("important","important.is.empty.error","The task importance field cannot be blank.");
        }
        else if(!tasksDto.getImportant().matches(importantRegex)){
            errors.rejectValue("important","important.is.wrong.format.error","The task importance field can only contain letters.");
        }
        else if(!tasksDto.getCreatedDate().matches(dateRegex)){
            errors.rejectValue("createdDate","createdDate.is.wrong.format.error","The date field was entered in the wrong format.");
        }
    }
}
