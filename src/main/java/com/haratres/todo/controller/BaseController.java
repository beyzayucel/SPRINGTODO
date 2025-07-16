package com.haratres.todo.controller;
import com.haratres.todo.exception.WebserviceValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BaseController {

    protected ModelMapper modelMapper() {
        return new ModelMapper();
    }

    protected void validate(Object object, String objectName, Validator validator)
    {
        Errors errors = new BeanPropertyBindingResult(object, objectName);
        validator.validate(object, errors);
        if (errors.hasErrors())
        {
            throw new WebserviceValidationException(errors);
        }
    }
}


