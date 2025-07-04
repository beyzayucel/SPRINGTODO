package com.haratres.todo.config;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Validationhandler {


    public List<Map<String, String>> validate(Errors errors) {

        if (errors.hasErrors()) {
            List<Map<String, String>> errorList = errors.getAllErrors().stream()
                    .map(error -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("code", error.getCode());
                        map.put("message", error.getDefaultMessage());
                        return map;
                    })
                    .collect(Collectors.toList());
            return errorList;
        }
        return null;
    }
}
