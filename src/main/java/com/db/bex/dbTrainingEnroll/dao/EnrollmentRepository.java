package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByUserId(long id);
//    @Query(value = "SELECT enrollment_id FROM enrollment WHERE enrollment.user_id = ? AND enrollment.training_id = ?", nativeQuery = true)
    List<Enrollment> findAllByUserIdAndTrainingId(Long user_id, Long training_id);

    List<Enrollment> findAllByTrainingId(long id);
}
