package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.dto.PopularityDto;
import com.db.bex.dbTrainingEnroll.entity.EnrollmentStatusType;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByIdIn(List<Long> id);
    Training findById(long id);

    @Query("SELECT count(e) FROM Enrollment e " +
            "where e.status = 'ACCEPTED'")
    Integer countAllAcceptedTrainings();

    @Query("SELECT count(e) FROM Enrollment e " +
            "join e.training t " +
            "where e.status = 'ACCEPTED' and t.category = 'SOFT'")
    Integer countAcceptedSoftTrainings();

    @Query("SELECT count(e) FROM Enrollment e " +
            "join e.training t " +
            "where e.status = 'ACCEPTED' and t.category = 'TECHNICAL'")
    Integer countAcceptedTechTraining();

    @Query("SELECT new com.db.bex.dbTrainingEnroll.dto.PopularityDto(" +
                    "t.technology, " +
                    "count(e.id)) " +
            "FROM Enrollment e " +
            "JOIN e.training t " +
            "WHERE t.category = ?1 and e.status = 'ACCEPTED' " +
            "GROUP BY t.technology")
    List<PopularityDto> countAcceptedTrainingsForEachTechnology(TrainingCategoryType trainingCategoryType);

}