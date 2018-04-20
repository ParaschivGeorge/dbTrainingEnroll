package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDtoTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    public TrainingService(TrainingDtoTransformer trainingDtoTransformer, EnrollmentRepository enrollmentRepository, TrainingRepository trainingRepository) {
        this.trainingDtoTransformer = trainingDtoTransformer;
        this.enrollmentRepository = enrollmentRepository;
        this.trainingRepository = trainingRepository;
    }

    private TrainingDtoTransformer trainingDtoTransformer;

    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public List<TrainingDto> findPendingTrainings(Long id) {
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants(id));
    }

    public List<TrainingDto> findTrainings() {
        return trainingDtoTransformer.getTrainings(trainingRepository.findAll());
    }

}
