package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dto.EnrollmentDetailsDto;
import com.db.bex.dbTrainingEnroll.dto.UserDto;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    public EnrollmentDetailsDto transform(Enrollment enrollment){

        return EnrollmentDetailsDto.builder()
                .userEmail(enrollment.getUser().getMail())
                .comment(enrollment.getManagerComment())
                .trainingType(enrollment.getTrainingType())
                .urgencyType(enrollment.getUrgency())
                .build();
    }

    public List<EnrollmentDetailsDto> getUserSubordinates(List<Enrollment> enrollments) {
        return enrollments.stream().map(this::transform).collect(Collectors.toList());
    }
}
