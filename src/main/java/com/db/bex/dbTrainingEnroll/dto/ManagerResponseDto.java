package com.db.bex.dbTrainingEnroll.dto;

import java.util.List;

public class ManagerResponseDto {
    private long trainingId;
    private List<String> emails;

    public ManagerResponseDto() {
    }

    public ManagerResponseDto(Long trainingId, List<String> emails) {
        this.trainingId = trainingId;
        this.emails = emails;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
