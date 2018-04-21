package com.db.bex.dbTrainingEnroll.dto;

import java.util.List;

public class UserStatusDto {

    private String mailUser;
    private Long idTraining;
    private Long status;

    public UserStatusDto(String mailUser, Long idTraining, Long status) {
        this.mailUser = mailUser;
        this.idTraining = idTraining;
        this.status = status;
    }

    public UserStatusDto() {
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public Long getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(Long idTraining) {
        this.idTraining = idTraining;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
