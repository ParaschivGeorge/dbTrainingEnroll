package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.Recommender;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.service.EmailService;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class UserController {

    private TrainingRepository trainingRepository;
    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    @Qualifier("dataSource1")
    private DataSource dataSource;


    public UserController(TrainingRepository trainingRepository, UserService userService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
    }

    @PostMapping("/subordinates")
    public List<UserDto> getSubordinates(@RequestBody ManagerRequestDto managerRequestDto){
        String email = managerRequestDto.getEmail();
        Long id = managerRequestDto.getId();
        System.out.println(email + " " + id);
        if(email != null && id != null)
            return userService.findSubordinates(email, id);
        else
            return null;
    }

    @PostMapping("/pendingUsers")
    public List<UserDto> getUserTrainings(@RequestBody ManagerTrainingRequestDto managerTrainingRequestDto) {
        String email = managerTrainingRequestDto.getEmail();
        Long id = managerTrainingRequestDto.getId();
        return userService.findPendingUsers(id, email);
    }

    @PostMapping("/subordinatesResult")
    public void saveSubordinates(@RequestBody ManagerResponseDto managerResponseDto) {
        //TODO : Remove Recommender from method and make separate method, uncomment functionality
        Long trainingId = managerResponseDto.getTrainingId();
        List<String> emails = managerResponseDto.getEmails();
        System.out.println(trainingId);
        if(trainingId !=null && emails.size() > 0)
            userService.savePendingSubordinates(trainingId, emails);
    }

    @PostMapping("/approveList")
    public void postUserStatus(@RequestBody List<UserStatusDto> userStatusDto) {
        //TODO : Enable email functionality, eliminate hard coding
        userService.saveSubordinatesStatusAndSendEmail(userStatusDto);
    }

    @PostMapping("/getUserData")
    public UserDto getUserData(@RequestBody EmailDto emailDto) {
        return userService.getUserData(emailDto);
    }

    @GetMapping("/crapa")
    public Training getTraining() {
        return trainingRepository.findById(3);
    }

    @PostMapping("/recommend")
    public List<TrainingDto> recommend(@RequestBody EmailDto emailDto){
        Recommender recommender = new Recommender(trainingRepository,dataSource);
//        System.out.println(emailDto.getEmail());
//        List<Long> items = recommender.recommendTraining(userRepository.findByMail(email).getId(),2);
        List<Long> items = recommender.recommendTraining(userRepository.findByMail(emailDto.getEmail()).getId(),2);
        return userService.findRecommendedTrainings(items);
    }

    @GetMapping("/register")
    public void register() {
        userService.addUser();
    }
}
