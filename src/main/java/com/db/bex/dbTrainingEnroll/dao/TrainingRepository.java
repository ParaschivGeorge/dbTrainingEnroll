package com.db.bex.dbTrainingEnroll.dao;

import com.db.bex.dbTrainingEnroll.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

}
