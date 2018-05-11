package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.entity.Notification;
import com.db.bex.dbTrainingEnroll.entity.NotificationStatus;
import com.db.bex.dbTrainingEnroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>  {
    List<Notification> findAllByUserId(Long id);
    List<Notification> findAllByUserIdAndStatus(Long id, NotificationStatus status);
}
