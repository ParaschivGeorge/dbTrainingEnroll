package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.UserDTO;
import com.db.bex.dbTrainingEnroll.dto.UserDTOTransformer;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDTOTransformer userDTOTransformer;
    private EnrollmentRepository enrollmentRepository;

    public UserService(UserRepository userRepository, UserDTOTransformer userDTOTransformer, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.userDTOTransformer = userDTOTransformer;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<UserDTO> findSubordinates(String email, long trainingId){
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> users = userRepository.findAllByManagerId(userRepository.findByMail(email).getId());
        if (users == null)
            return null;
        System.out.println(users);
        for (User user : users) {
            List<Enrollment> enrollments = enrollmentRepository.findAllByUserIdAndTrainingId(user.getId(), trainingId);
            System.out.println(user.getMail());
            System.out.println(enrollments);
            if (enrollments.isEmpty()) {
                userDTOList.add(userDTOTransformer.transform(user));
            }
        }
        return userDTOList;
    }

}
