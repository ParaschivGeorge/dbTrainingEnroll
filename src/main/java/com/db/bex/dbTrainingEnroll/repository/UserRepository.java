package com.db.bex.dbTrainingEnroll.repository;

import com.db.bex.dbTrainingEnroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

}
