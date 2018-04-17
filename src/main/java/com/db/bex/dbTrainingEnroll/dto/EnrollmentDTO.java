package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.EnrollmentStatusType;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.User;

public class EnrollmentDTO {

    private Long id;
    private Training training;
    private User user;
    private EnrollmentStatusType statusType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnrollmentStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(EnrollmentStatusType statusType) {
        this.statusType = statusType;
    }
}
