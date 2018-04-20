package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.service.TrainingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pendingTrainings")
    public List<TrainingDto> getPendingTrainings(@RequestParam Long id){
        return trainingService.findPendingTrainings(id);
    }

}
