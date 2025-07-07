package com.haratres.todo.controller;

import org.modelmapper.ModelMapper;

public class BaseController {

    ModelMapper modelMapper;

    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}


