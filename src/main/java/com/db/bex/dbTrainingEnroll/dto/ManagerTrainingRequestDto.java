package com.db.bex.dbTrainingEnroll.dto;

public class ManagerTrainingRequestDto {
    private String email;
    private Long id;

    public ManagerTrainingRequestDto(String email, Long id) {
        this.email = email;
        this.id = id;
    }

    public ManagerTrainingRequestDto() {
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
