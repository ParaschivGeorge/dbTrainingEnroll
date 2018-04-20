package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.UserDto;
import com.db.bex.dbTrainingEnroll.service.TrainingService;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainingController {

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    private TrainingService trainingService;

    @GetMapping("/trainings")
    public List<TrainingDto> getTrainings(){
        return trainingService.findTrainings();
    }

    @PostMapping("/pendingTrainings")
    public List<TrainingDto> getPendingTrainings(@RequestBody String email) {
        return trainingService.findPendingTrainings(email);
    }
}
