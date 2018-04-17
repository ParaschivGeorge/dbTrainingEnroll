package com.db.bex.dbTrainingEnroll.repository;

import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

//    List<Enrollment> findAllByStatusAndTraining(long idStatus, long idTraining);
    List<Enrollment> findAllByStatusAndTraining(Enum status, long idTraining);

}
