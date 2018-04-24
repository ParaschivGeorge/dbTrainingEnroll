package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.dto.PopularityDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDto;
import com.db.bex.dbTrainingEnroll.dto.TrainingDtoTransformer;
import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import com.db.bex.dbTrainingEnroll.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService {

    private TrainingDtoTransformer trainingDtoTransformer;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public List<TrainingDto> findPendingTrainings(EmailDto email) {

        if(email == null) {
            throw new NullPointerException("Email is null");
        }

        User user = userRepository.findByMail(email.getEmail());
        if (user == null)
            return null;
        Long id = user.getId();
        if(id == null) {
            throw new NullPointerException("Email does not exist");
        }
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants(id));
    }

    public List<TrainingDto> findTrainings() {
        return trainingDtoTransformer.getTrainings(trainingRepository.findAll());
    }

    public Integer countAcceptedUsers(Long idTraining) {
        return enrollmentRepository.countAcceptedUsers(idTraining);
    }

    public Integer[] countAcceptedSoftTrainings() {
        Integer acceptedSoft = trainingRepository.countAcceptedSoftTrainings();
        Integer numberOfTrainings = trainingRepository.countAllAcceptedTrainings();
        Integer[] softReport = {acceptedSoft, numberOfTrainings};
        return softReport;
    }

    public Integer[] countAcceptedTechTrainings() {
        Integer acceptedTech = trainingRepository.countAcceptedTechTraining();
        Integer numberOfTrainings = trainingRepository.countAllAcceptedTrainings();
        Integer[] techReport = {acceptedTech, numberOfTrainings};
        return techReport;
    }

    public List<PopularityDto> countTopTechnicalAttendees() {
        return trainingRepository.countAcceptedTrainingsForEachCategory(TrainingCategoryType.TECHNICAL);
    }

    public List<PopularityDto> countTopSoftAttendees() {
        return trainingRepository.countAcceptedTrainingsForEachCategory(TrainingCategoryType.SOFT);
    }

    public List<PopularityDto> countTopAllAttendees() {
        return trainingRepository.countAcceptedTrainingsForAllCategories();
    }
}
