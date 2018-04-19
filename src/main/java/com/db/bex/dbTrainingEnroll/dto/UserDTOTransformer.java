package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOTransformer {

    public UserDTOTransformer(UserRepository userRepository, TrainingRepository trainingRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    UserRepository userRepository;

    TrainingRepository trainingRepository;

    EnrollmentRepository enrollmentRepository;

    public List<UserDto> getUserSubordinates(List<User> user, long id){
//        return user.stream().map(this::transform).collect(Collectors.toList());
        List<UserDto> userDtoList = user.stream().map(this::transform).collect(Collectors.toList());
        return this.filterUsers(userDtoList, id);
    }


    public UserDto transform(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setMail(user.getMail());
        userDto.setUserType(user.getType());
        return userDto;
    }

    public List<UserDto> filterUsers(List<UserDto> listDTO, long id){
        List<UserDto> newList = new ArrayList<>();
        for(UserDto i: listDTO){
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
