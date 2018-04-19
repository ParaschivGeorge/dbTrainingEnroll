package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<TrainingDto> getTrainings(@RequestParam(required = false) Long id){
        return trainingService.findSpecialTrainings();
    }

}