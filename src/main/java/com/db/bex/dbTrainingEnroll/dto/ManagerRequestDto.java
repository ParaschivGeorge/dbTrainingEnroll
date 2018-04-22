package com.db.bex.dbTrainingEnroll.dto;

public class ManagerRequestDto {
    private String email;
    private Long id;

    public ManagerRequestDto() {
    }

    public ManagerRequestDto(String email, Long id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}