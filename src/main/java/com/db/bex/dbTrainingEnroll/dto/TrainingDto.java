package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;

import java.util.Date;

public class TrainingDto {

    private Long id;
    private String name;
    private String date;
    private String duration;
    private String technology;
    private TrainingCategoryType categoryType;
    private String acceptedUsers;

    //    private Date startDate;
//    private Date endDate;

    public TrainingDto() {
    }

    public TrainingDto(Training training) {
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

//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public TrainingCategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(TrainingCategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getAcceptedUsers() {
        return acceptedUsers;
    }

    public void setAcceptedUsers(String acceptedUsers) {
        this.acceptedUsers = acceptedUsers;
    }
}
