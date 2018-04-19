package com.db.bex.dbTrainingEnroll.entity;

public enum EnrollmentStatusType {
    ACCEPTED ("ACCEPTED"),
    PENDING ("PENDING");

    private final String status;

    EnrollmentStatusType(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
