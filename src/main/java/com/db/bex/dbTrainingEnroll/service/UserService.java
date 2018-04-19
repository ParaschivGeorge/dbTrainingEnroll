package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dto.UserDTO;
import com.db.bex.dbTrainingEnroll.dto.UserDTOTransformer;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
