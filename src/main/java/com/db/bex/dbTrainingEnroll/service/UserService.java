package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.UserDto;
import com.db.bex.dbTrainingEnroll.dto.UserDtoTransformer;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.EnrollmentStatusType;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDtoTransformer userDtoTransformer;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public UserService(UserRepository userRepository, UserDtoTransformer userDtoTransformer, EnrollmentRepository enrollmentRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.userDtoTransformer = userDtoTransformer;
        this.enrollmentRepository = enrollmentRepository;
        this.trainingRepository = trainingRepository;
    }

    public List<UserDto> findSubordinates(String email, long trainingId){
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = userRepository.findAllByManagerId(userRepository.findByMail(email).getId());
        if (users == null)
            return null;
        System.out.println(users);
        for (User user : users) {
            List<Enrollment> enrollments = enrollmentRepository.findAllByUserIdAndTrainingId(user.getId(), trainingId);
            System.out.println(user.getMail());
            System.out.println(enrollments);
            if (enrollments.isEmpty()) {
                userDtoList.add(userDtoTransformer.transform(user));
            }
        }
        return userDtoList;
    }

    public List<UserDto> findPendingUsers(Long idTraining, String email) {

        Long idPm = userRepository.findByMail(email).getId();

        return userDtoTransformer.getUserSubordinates1(userRepository.findPendingUsers(idTraining, idPm));
    }

    public void savePendingSubordinates(long idTraining, List<String> emails){
        for(String s:emails) {
            if((enrollmentRepository.findByUserIdAndTrainingId(userRepository.findByMail(s).getId(),idTraining) != null)
                    || (enrollmentRepository.findByTrainingId(idTraining) == null))
                return;
            else {
                Enrollment enrollment = new Enrollment();
                enrollment.setStatus(EnrollmentStatusType.PENDING);
                enrollment.setStatus(EnrollmentStatusType.PENDING);
                enrollment.setTraining(trainingRepository.findById(idTraining));
                enrollment.setUser(userRepository.findByMail(s));
                enrollmentRepository.save(enrollment);
            }
        }
    }

    public void saveSubordinatesStatus(String emailUser, Long idTraining, Long status) {

        Long id = userRepository.findByMail(emailUser).getId();

        Enrollment enrollment = enrollmentRepository.findByUserIdAndTrainingId(id, idTraining);

        if (enrollment != null) {

            if (status == 1) {
                enrollment.setStatus(EnrollmentStatusType.ACCEPTED);
                enrollmentRepository.save(enrollment);
            }

            if (status == 0)
                enrollmentRepository.delete(enrollment);
        }
    }
}
