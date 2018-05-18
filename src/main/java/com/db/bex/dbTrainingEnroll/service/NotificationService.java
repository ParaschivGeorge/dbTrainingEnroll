package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.NotificationRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.entity.Notification;
import com.db.bex.dbTrainingEnroll.entity.NotificationStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private UserRepository userRepository;
    private NotificationRepository notificationRepository;

    public NotificationService(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications(EmailDto emailDto) {
        List<Notification> notifications = notificationRepository
                .findAllByUserIdOrderByDateDesc(userRepository.findByMail(emailDto.getEmail()).getId());
        List<Notification> saveNotifications = new ArrayList<>();

        for (Notification notification : notifications) {
            saveNotifications.add(new Notification(notification));
        }

        notificationRepository.saveAll(saveNotifications);

        return notifications;
    }

    public List<Notification> getNewNotifications(EmailDto emailDto) {
        return notificationRepository.findAllByUserIdAndStatusOrderByDateDesc(userRepository.findByMail(emailDto.getEmail()).getId(),
                NotificationStatus.NEW);
    }
}
