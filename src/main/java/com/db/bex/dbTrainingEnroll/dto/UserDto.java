package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.entity.UserType;

public class UserDto {

    private long id;
    private String name;
    private String mail;
    private UserType userType;
//    private String userType;
//    private User manager;
//    private List<UserDto> subordinates;
//    private List<Training> enrollments;

    public UserDto() {
    }

    public UserDto(User user) {
    }
//    private Set<Enrollment> enrollments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }

        public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

//    public User getManager() {
//        return manager;
//    }
//
//    public void setManager(User manager) {
//        this.manager = manager;
//    }

//    public List<UserDto> getSubordinates() {
//        return subordinates;
//    }
//
//    public void setSubordinates(List<User> subordinates) {
//        this.subordinates = subordinates.stream().map(UserDto::new).collect(Collectors.toList());
//    }

}