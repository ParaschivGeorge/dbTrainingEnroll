package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDtoTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    public TrainingService(TrainingDtoTransformer trainingDtoTransformer, EnrollmentRepository enrollmentRepository) {
        this.trainingDtoTransformer = trainingDtoTransformer;
        this.enrollmentRepository = enrollmentRepository;
    }

    private TrainingDtoTransformer trainingDtoTransformer;

    private EnrollmentRepository enrollmentRepository;

    public List<TrainingDto> findSpecialTrainings() {
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants());
    }

}
