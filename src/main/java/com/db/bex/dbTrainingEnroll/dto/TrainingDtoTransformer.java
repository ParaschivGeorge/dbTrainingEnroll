package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.entity.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingDtoTransformer {

    public TrainingDtoTransformer(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    TrainingRepository trainingRepository;

    public List<TrainingDto> getTrainings(List<Training> training){
        return training.stream().map(this::transform).collect(Collectors.toList());
    }


    public TrainingDto transform(Training training){
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setId(training.getId());
        trainingDto.setName(training.getName());
        trainingDto.setStartDate(training.getStartDate());
        trainingDto.setEndDate(training.getEndDate());
        return trainingDto;
    }

}
