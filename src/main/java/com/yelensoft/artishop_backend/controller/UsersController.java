package com.yelensoft.artishop_backend.controller;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.model.Users;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;
import com.yelensoft.artishop_backend.service.UsersService;
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
    public ResponseEntity<Object> addUsers(@Valid @RequestBody Users users){
        return ResponseHandler.generateResponse("susses", HttpStatus.OK,usersService.addUsers(users));
    }

    @GetMapping("/connect")
    public ResponseEntity<Object> connectUsers(@Valid @RequestBody AuthPojo authPojo){
        return ResponseHandler.generateResponse("susses", HttpStatus.OK,usersService.connectUsers(authPojo));
    }
}
