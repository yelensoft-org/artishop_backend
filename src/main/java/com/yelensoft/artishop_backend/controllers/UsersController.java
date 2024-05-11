package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.UserApp;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;
import com.yelensoft.artishop_backend.services.UsersService;
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
    public ResponseEntity<Object> addUsers(@Valid @RequestBody UserApp userApp){
        return ResponseHandler.generateResponse("success", HttpStatus.OK,usersService.addUsers(userApp));
    }

    @GetMapping("/connect")
    public ResponseEntity<Object> connectUsers(@Valid @RequestBody AuthPojo authPojo){
        return ResponseHandler.generateResponse("success", HttpStatus.OK,usersService.connectUsers(authPojo));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserApp userApp){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, usersService.updateUser(userApp));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        return ResponseHandler.generateResponse("success", HttpStatus.OK,usersService.deleteUser(id));
    }
}
