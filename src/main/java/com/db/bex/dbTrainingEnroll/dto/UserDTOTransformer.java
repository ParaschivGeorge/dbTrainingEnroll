package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDTOTransformer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    public List<UserDTO> getUserSubordinates(List<User> user, long id){
//        return user.stream().map(this::transform).collect(Collectors.toList());
        List<UserDTO> userDTOList = user.stream().map(this::transform).collect(Collectors.toList());
        return this.filterUsers(userDTOList, id);
    }


    public UserDTO transform(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setMail(user.getMail());
        userDTO.setUserType(user.getType());
//        userDTO.setEnrollments(enrollmentRepository.findAllByUserId(user.getId()));
//        userDTO.setSubordinates(user.getSubordinates());
//
        return userDTO;
    }

    public List<UserDTO> filterUsers(List<UserDTO> listDTO, long id){
        List<UserDTO> newList = new ArrayList<>();
        for(UserDTO i: listDTO){
            List<Enrollment> list = enrollmentRepository.findAllByTrainingId(id);
            System.out.println(list);
            int ok = 0;
            for(Enrollment j : list)
                if (listDTO.contains(this.transform(j.getUser())))
                    ok = 1;
            if(ok == 0)
                newList.add(i);
        }
        System.out.println(newList);
        return newList;
    }
}
