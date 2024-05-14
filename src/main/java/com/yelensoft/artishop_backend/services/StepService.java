package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.Step;
import com.yelensoft.artishop_backend.repositories.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class StepService {

    @Autowired
    private StepRepository stepRepository;

    public Step createStep(Step step) {
        return stepRepository.save(step);
    }

    public Step getStepById(Long id) {
        Optional<Step> step = stepRepository.findById(id);
        return step.orElse(null);
    }

    public List<Step> getAllSteps() {
        return stepRepository.findAll();
    }

    public Step updateStep(Long id, Step stepDetails) {
        Step step = getStepById(id);
        if (step != null) {
            step.setTitle(stepDetails.getTitle());
            step.setNbStep(stepDetails.getNbStep());
            step.setVideoUrl(stepDetails.getVideoUrl());
            step.setCreationDate(stepDetails.getCreationDate());
            step.setUpdateDate(stepDetails.getUpdateDate());
            step.setProduct(stepDetails.getProduct());
            return stepRepository.save(step);
        } else {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStep(@PathVariable Long id){
        stepRepository.deleteById(id);
        String message = "Step deleted successfully";
        return ResponseHandler.generateResponse("success", HttpStatus.OK, message);
    }
}