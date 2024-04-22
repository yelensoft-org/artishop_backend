package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.services.UsersService;
import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.model.User;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/add")
    public ResponseEntity<Object> addUsers(@Valid @RequestBody User users){
        return ResponseHandler.generateResponse("susses", HttpStatus.OK,usersService.addUsers(users));
    }

    @GetMapping("/connect")
    public ResponseEntity<Object> connectUsers(@Valid @RequestBody AuthPojo authPojo){
        return ResponseHandler.generateResponse("susses", HttpStatus.OK,usersService.connectUsers(authPojo));
    }
}
