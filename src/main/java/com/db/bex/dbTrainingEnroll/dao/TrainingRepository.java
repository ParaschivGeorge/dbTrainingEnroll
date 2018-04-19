package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByIdIn(List<Long> id);
    Training findById(long id);
}
