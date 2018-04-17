package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrollmentDTOTransformer {

    public List<EnrollmentDTO> enrollmentDTOList(Iterable<Enrollment> it){
        return ((List<Enrollment>) it).stream().map(this::transform).collect(Collectors.toList());
    }

    public EnrollmentDTO transform(Enrollment enrollment){
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(enrollment.getId());
        enrollmentDTO.setTraining(enrollment.getTraining());
        enrollmentDTO.setUser(enrollment.getUser());
        enrollmentDTO.setStatusType(enrollment.getStatus());
        return enrollmentDTO;
    }

}
