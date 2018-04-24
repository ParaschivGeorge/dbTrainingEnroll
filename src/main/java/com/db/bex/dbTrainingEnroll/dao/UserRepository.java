package com.db.bex.dbTrainingEnroll.dao;


import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.entity.UserGenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByName(String name);

    List<User> findAllByManagerId(long id);

    User findByMail(String email);

    Integer countByGender(UserGenderType genderType);

    @Query("select u from Enrollment e " +
            "join e.user u where u.manager.id IN " +
            "(select uu.id from User uu where uu.manager.id=?2) " +
            "and e.status = 'PENDING'" +
            "and e.training.id =?1")
    List<User> findPendingUsers (Long idTraining, Long idPm);



}
