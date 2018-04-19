package com.db.bex.dbTrainingEnroll.dao;

        import com.db.bex.dbTrainingEnroll.entity.Enrollment;
        import com.db.bex.dbTrainingEnroll.entity.Training;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;
        import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select t from Enrollment e join e.training t where e.status = 'PENDING' ")
    List<Training> findTrainingsThatHavePendingParticipants();
}
