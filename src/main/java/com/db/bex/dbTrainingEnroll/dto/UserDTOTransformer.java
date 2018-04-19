package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDTOTransformer {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getUserSubordinates(List<User> user){
//        System.out.println(user.getName()+ " " + user.getSubordinates());
        return user.stream().map(this::transform).collect(Collectors.toList());
    }

    public UserDTO transform(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setMail(user.getMail());
        userDTO.setUserType(user.getType());
        return userDTO;
    }
}
