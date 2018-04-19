package com.db.bex.dbTrainingEnroll.dao;


import com.db.bex.dbTrainingEnroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByName(String name);

    List<User> findAllByManagerId(long id);

    User findByMail(String email);

}
