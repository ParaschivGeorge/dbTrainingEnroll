package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.exceptions.MissingDataException;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/subordinates")
    public List<UserDto> getSubordinates(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        String email = managerRequestDto.getEmail();
        Long id = managerRequestDto.getId();
        return userService.findSubordinates(email, id);
    }

    @PostMapping("/pendingUsers")
    public List<EnrollmentDetailsDto> getUserTrainings(@RequestBody ManagerTrainingRequestDto managerTrainingRequestDto) throws MissingDataException {
        String email = managerTrainingRequestDto.getEmail();
        Long id = managerTrainingRequestDto.getId();
        return userService.findPendingUsers(id, email);
    }

    @PostMapping("/subordinatesResult")
    public void saveSubordinates(@RequestBody ManagerResponseDto managerResponseDto) throws MissingDataException {
        userService.savePendingSubordinates(managerResponseDto);
    }

    @PostMapping("/approveList")
    public void postUserStatus(@RequestBody List<UserStatusDto> userStatusDto) throws MissingDataException {
        //TODO : Enable email functionality, eliminate hard coding
        userService.saveSubordinatesStatusAndSendEmail(userStatusDto);
    }

    @PostMapping("/getUserData")
    public UserDto getUserData(@RequestBody EmailDto emailDto) {
        return userService.getUserData(emailDto);
    }

//    @PostMapping("/recommend")
//    public List<TrainingDto> recommend(@RequestBody EmailDto emailDto){
//        return userService.findRecommendedTrainings(userRepository.findByMail(emailDto.getEmail()).getId());
//    }

    @GetMapping("/register")
    public void register() {
        userService.addUser();
    }

    @PostMapping("/userSelfEnroll")
    public void userSelfEnroll(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        userService.saveUserSaveEnroll(managerRequestDto);
    }

    @PostMapping("/getSelfEnrolled")
    public List<UserDto> getUsersSelfEnrolled(@RequestBody ManagerRequestDto managerRequestDto) throws MissingDataException {
        return userService.findSelfEnrolledSubordinates(managerRequestDto);
    }

    @GetMapping("/genderStats")
    public Integer[] getGendersDiff() {
        return userService.getGenderCount();
    }


}
