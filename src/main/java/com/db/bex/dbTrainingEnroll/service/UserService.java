package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dto.UserDTO;
import com.db.bex.dbTrainingEnroll.dto.UserDTOTransformer;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOTransformer userDTOTransformer;

    public List<UserDTO> findSubordinates(String name){
//        return userDTOTransformer.getUserSubordinates(userRepository.findByName(name));
        return userDTOTransformer.getUserSubordinates(userRepository.findAllByManagerId(userRepository.findByName(name).getId()));
    }

}
