package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDtoTransformer;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.User;
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

    public List<TrainingDto> findPendingTrainings(EmailDto email) {
        User user = userRepository.findByMail(email.getEmail());
        if (user == null)
            return null;
        long id = user.getId();
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants(id));
    }

    public List<TrainingDto> findTrainings() {
        return trainingDtoTransformer.getTrainings(trainingRepository.findAll());
    }

    public Integer countAcceptedUsers(Long idTraining) {
        return enrollmentRepository.countAcceptedUsers(idTraining);
    }

}
