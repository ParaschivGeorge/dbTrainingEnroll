package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDtoTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    public TrainingService(UserRepository userRepository, TrainingDtoTransformer trainingDtoTransformer, EnrollmentRepository enrollmentRepository, TrainingRepository trainingRepository) {
        this.trainingDtoTransformer = trainingDtoTransformer;
        this.enrollmentRepository = enrollmentRepository;
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    private TrainingDtoTransformer trainingDtoTransformer;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public List<TrainingDto> findPendingTrainings(String email) {
        long id = userRepository.findByMail(email).getId();
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants(id));
    }

    public List<TrainingDto> findTrainings() {
        return trainingDtoTransformer.getTrainings(trainingRepository.findAll());
    }

}
