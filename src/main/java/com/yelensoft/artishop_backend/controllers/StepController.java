package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.Step;
import com.yelensoft.artishop_backend.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/steps")
public class StepController {

    @Autowired
    private StepService stepService;

    @PostMapping("/add")
    public ResponseEntity<Object> addStep(@Valid @RequestBody Step step){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, stepService.createStep(step));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStepById(@PathVariable Long id){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, stepService.getStepById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllSteps(){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, stepService.getAllSteps());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateStep(@PathVariable Long id, @Valid @RequestBody Step stepDetails){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, stepService.updateStep(id, stepDetails));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStep(@PathVariable Long id){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, stepService.deleteStep(id));
    }
}