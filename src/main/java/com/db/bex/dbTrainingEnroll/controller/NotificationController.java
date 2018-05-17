package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.entity.Notification;
import com.db.bex.dbTrainingEnroll.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("/getAllNotifications")
    public List<Notification> getAllNotifications(@RequestBody EmailDto emailDto) {
        return notificationService.getAllNotifications(emailDto);
    }

    @PostMapping("/getNewNotifications")
    public List<Notification> getNewNotifications(@RequestBody EmailDto emailDto) {
        return notificationService.getNewNotifications(emailDto);
    }

}
