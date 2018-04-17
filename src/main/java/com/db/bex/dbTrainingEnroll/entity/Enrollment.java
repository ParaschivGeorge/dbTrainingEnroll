package com.db.bex.dbTrainingEnroll.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Enrollment")
public class Enrollment implements Serializable {

    @Id
    @Column(name = "enrollment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatusType status;

    public Enrollment() {
    }

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

    public EnrollmentStatusType getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatusType status) {
        this.status = status;
    }
}
