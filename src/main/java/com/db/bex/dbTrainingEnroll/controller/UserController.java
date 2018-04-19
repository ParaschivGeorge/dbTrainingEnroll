package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.UserDTO;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.service.EmailService;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    public UserController(UserService userService) {
        this.userService = userService;
    }

    private TrainingRepository trainingRepository;

    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/subordinates")
    public List<UserDTO> getSubordinates(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) Long id){
//        List<String> list = new ArrayList<>();
//        list.add("stefaneva25@yahoo.com");
//        list.add("stefaneva52@gmail.com");
//        try {
//            emailService.sendEmail(list,"Welcome","Subject");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        return userService.findSubordinates(email, id);
    }

    @GetMapping("/crapa")
    public Training getTraining() {
        return trainingRepository.findById(3);
    }
}
