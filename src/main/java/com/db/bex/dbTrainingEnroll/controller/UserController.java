package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.UserDTO;
import com.db.bex.dbTrainingEnroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping("/subordinates")
    public List<UserDTO> getSubordinates(@RequestParam(required = false) String name){
        return userService.findSubordinates(name);
    }
}
