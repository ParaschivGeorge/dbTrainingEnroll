package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.ManagerRequestDto;
import com.db.bex.dbTrainingEnroll.dto.UserDto;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.service.EmailService;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    public UserController(TrainingRepository trainingRepository, UserService userService, EmailService emailService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    private TrainingRepository trainingRepository;

    private UserService userService;
    
    private EmailService emailService;

//    @GetMapping("/subordinates")
//    public List<UserDto> getSubordinates(@RequestParam(required = false) String email,
//                                         @RequestParam(required = false) Long id){
//        List<String> list = new ArrayList<>();
//        list.add("stefaneva25@yahoo.com");
//        list.add("stefaneva52@gmail.com");
//        try {
//            emailService.sendEmail(list,"Welcome","Subject");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return userService.findSubordinates(email, id);
//    }

    @PostMapping("/subordinates")
    public List<UserDto> getSubordinates(@RequestBody ManagerRequestDto managerRequestDto){
        String email = managerRequestDto.getEmail();
        Long id = managerRequestDto.getId();
        System.out.println(email + " " + id);
        return userService.findSubordinates(email, id);
    }

    @GetMapping("/crapa")
    public Training getTraining() {
        return trainingRepository.findById(3);
    }
}
