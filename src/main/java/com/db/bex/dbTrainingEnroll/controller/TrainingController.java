package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.dto.MonthlyReportDto;
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

    @GetMapping("/attendedTrainings")
    public Integer[] getAttendedTrainings() {
        return trainingService.countAcceptedTrainings();
    }

    @GetMapping("/topTechnicalAttendees")
    public List<PopularityDto> getTopTechincalAttendees() {
        return trainingService.countTopTechnicalAttendees();
    }

    @GetMapping("/topSoftAttendees")
    public List<PopularityDto> getTopSoftAttendees() {
        return trainingService.countTopSoftAttendees();
    }

    @GetMapping("/topAllAttendees")
    public List<PopularityDto> getTopAllAttendees() {
        return trainingService.countTopAllAttendees();
    }

    @GetMapping("/reportByMonth")
    public List<MonthlyReportDto> getReportByMonth() {
        return trainingService.findMonthlyReport();
    }
}
