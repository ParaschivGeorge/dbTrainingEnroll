package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Notification;
import com.db.bex.dbTrainingEnroll.exceptions.MissingDataException;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/subordinates")
    public List<UserDto> getSubordinates(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        return userService.findSubordinates(managerRequestDto);
    }

    //users waiting for SPOC approval
    @PostMapping("/pendingUsers")
    public List<EnrollmentDetailsDto> getUserTrainings(@RequestBody ManagerTrainingRequestDto managerTrainingRequestDto) throws MissingDataException {
        return userService.findPendingUsers(managerTrainingRequestDto);
    }

    @PostMapping("/subordinatesResult")
    public void saveSubordinates(@RequestBody ManagerResponseDto managerResponseDto) throws MissingDataException {
        userService.savePendingSubordinates(managerResponseDto);
    }

    @PostMapping("/approveList")
    public void postUserStatus(@RequestBody List<UserStatusDto> userStatusDto) throws MissingDataException {
        userService.saveSubordinatesStatusAndSendEmail(userStatusDto);
    }

    @PostMapping("/getUserData")
    public UserDto getUserData(@RequestBody EmailDto emailDto) {
        return userService.getUserData(emailDto);
    }

    @PostMapping("/userSelfEnroll")
    public void userSelfEnroll(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        userService.saveUserSaveEnroll(managerRequestDto);
    }

    @PostMapping("/getSelfEnrolled")
    public List<UserDto> getUsersSelfEnrolled(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        return userService.findSelfEnrolledSubordinates(managerRequestDto);
    }

    @GetMapping("/register")
    public void register() {
        userService.addUser();
    }

    @GetMapping("/genderStats")
    public Integer[] getGendersDiff() {
        return userService.getGenderCount();
    }

//    @PostMapping("/recommend")
//    public List<TrainingDto> recommend(@RequestBody EmailDto emailDto){
//        return userService.findRecommendedTrainings(userRepository.findByMail(emailDto.getEmail()).getId());
//    }
}
