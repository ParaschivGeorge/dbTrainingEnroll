package com.db.bex.dbTrainingEnroll.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String mail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;
//    private String type;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
    private User manager;

//    @OneToMany(mappedBy="manager" , fetch = FetchType.EAGER)
//    private List<User> subordinates;
//
//    @OneToMany(mappedBy = "user")
//    private List<Enrollment> enrollments;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

//    public UserType getType() {
//        return type;
//    }
//
//    public void setType(UserType type) {
//        this.type = type;
//    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }


    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

//    public List<User> getSubordinates() {
//        return subordinates;
//    }
//
//    public void setSubordinates(List<User> subordinates) {
//        this.subordinates = subordinates;
//    }
//
//    public List<Enrollment> getEnrollments() {
//        return enrollments;
//    }
//
//    public void setEnrollments(List<Enrollment> enrollments) {
//        this.enrollments = enrollments;
//    }
}
