package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.exceptions.MissingDataException;
import com.db.bex.dbTrainingEnroll.service.EmailService;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private TrainingRepository trainingRepository;

    private UserService userService;

    public UserController(TrainingRepository trainingRepository, UserService userService, EmailService emailService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
    }

    @PostMapping("/subordinates")
    public List<UserDto> getSubordinates(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        String email = managerRequestDto.getEmail();
        Long id = managerRequestDto.getId();
        return userService.findSubordinates(email, id);
    }

    @PostMapping("/pendingUsers")
    public List<UserDto> getUserTrainings(@RequestBody ManagerTrainingRequestDto managerTrainingRequestDto) throws MissingDataException {
        String email = managerTrainingRequestDto.getEmail();
        Long id = managerTrainingRequestDto.getId();
        return userService.findPendingUsers(id, email);
    }

    @PostMapping("/subordinatesResult")
    public void saveSubordinates(@RequestBody ManagerResponseDto managerResponseDto) throws MissingDataException {
        //TODO : Remove Recommender from method and make separate method, uncomment functionality
        Long trainingId = managerResponseDto.getTrainingId();
        List<String> emails = managerResponseDto.getEmails();
        userService.savePendingSubordinates(trainingId, emails);

        //Recommender recommender = new Recommender(trainingRepository);
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

    @GetMapping("/register")
    public void register() {
        userService.addUser();
    }

    @GetMapping("/genderStats")
    public Integer[] getGendersDiff() {
        return userService.getGenderCount();
    }
}
