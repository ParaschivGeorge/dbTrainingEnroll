package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
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
        Long trainingId = managerResponseDto.getTrainingId();
        List<String> emails = managerResponseDto.getEmails();
        System.out.println(trainingId);
        if(trainingId !=null && emails.size() > 0)
            userService.savePendingSubordinates(trainingId, emails);
    }

    @PostMapping("/approveList")
    public void postUserStatus(@RequestBody List<UserStatusDto> userStatusDto) {

        for(UserStatusDto u : userStatusDto) {
            String mailUser = u.getMailUser();
            Long idTraining = u.getIdTraining();
            Long status = u.getStatus();

            userService.saveSubordinatesStatus(mailUser, idTraining, status);
        }

    }

    @GetMapping("/crapa")
    public Training getTraining() {
        return trainingRepository.findById(3);
    }

    @GetMapping("/register")
    public void register() {
        userService.addUser();
    }
}
