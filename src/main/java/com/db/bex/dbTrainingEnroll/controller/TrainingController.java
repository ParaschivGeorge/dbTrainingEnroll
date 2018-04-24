package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.dto.PopularityDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.service.TrainingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TrainingController {

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/trainings")
    public List<TrainingDto> getTrainings(){
        return trainingService.findTrainings();
    }

    @PostMapping("/pendingTrainings")
    public List<TrainingDto> getPendingTrainings(@RequestBody EmailDto email){
        return trainingService.findPendingTrainings(email);
    }

    @GetMapping("/attendedSoftTrainings")
    public Integer[] getSoftAttendedTrainings() {
        return trainingService.countAcceptedSoftTrainings();
    }

    @GetMapping("/attendedTechTrainings")
    public Integer[] getTechAttendedTrainings() {
        return trainingService.countAcceptedTechTrainings();
    }

    @GetMapping("/topDeTop")
    public List<PopularityDto> getTopTop() {
        return trainingService.countTopAttendees();
    }

}
