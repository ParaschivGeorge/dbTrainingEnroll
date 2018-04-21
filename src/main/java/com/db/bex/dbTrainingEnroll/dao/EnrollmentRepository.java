package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select t from Enrollment e join e.training t where e.status = 'PENDING' " +
            "and e.user.id IN (select u.id from User u where u.manager.id IN" +
            "(select uu.id from User uu where uu.manager.id =:id))")
        List<Training> findTrainingsThatHavePendingParticipants(@Param("id") Long id);


    List<Enrollment> findAllByUserId(long id);
//  @Query(value = "SELECT enrollment_id FROM enrollment WHERE enrollment.user_id = ? AND enrollment.training_id = ?", nativeQuery = true)
    List<Enrollment> findAllByUserIdAndTrainingId(Long user_id, Long training_id);

    List<Enrollment> findAllByTrainingId(long id);

    Enrollment findByUserIdAndTrainingId(Long id, Long idTraining);
}

