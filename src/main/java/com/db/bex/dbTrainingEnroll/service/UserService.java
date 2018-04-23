package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.EmailDto;
import com.db.bex.dbTrainingEnroll.dto.UserDto;
import com.db.bex.dbTrainingEnroll.dto.UserDtoTransformer;
import com.db.bex.dbTrainingEnroll.entity.Enrollment;
import com.db.bex.dbTrainingEnroll.entity.EnrollmentStatusType;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.entity.UserType;
import com.db.bex.dbTrainingEnroll.exceptions.MissingDataException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserDtoTransformer userDtoTransformer;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public List<UserDto> findSubordinates(String email, Long trainingId) throws MissingDataException {

        if(email == null || trainingId == null) {
            throw new MissingDataException("Email or id is null");
        }
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = userRepository.findAllByManagerId(userRepository.findByMail(email).getId());
        if (users == null)
            throw new MissingDataException("Email or id is null");
        for (User user : users) {
            List<Enrollment> enrollments = enrollmentRepository.findAllByUserIdAndTrainingId(user.getId(), trainingId);
            System.out.println(user.getMail());
            System.out.println(enrollments);
            if (enrollments.isEmpty()) {
                userDtoList.add(userDtoTransformer.transform(user));
            }
        }
        return userDtoList;
    }

    public List<UserDto> findPendingUsers(Long idTraining, String email) throws MissingDataException {

        Long idPm = userRepository.findByMail(email).getId();

        if(idPm == null) {
            throw new MissingDataException("Email does not exist");
        }

        return userDtoTransformer.getUserSubordinates1(userRepository.findPendingUsers(idTraining, idPm));

    }

    public void savePendingSubordinates(Long idTraining, List<String> emails) throws MissingDataException {

        if(idTraining == null || emails == null) {
            throw new MissingDataException("Id or email is null");
        }

        for(String s:emails) {
            if((enrollmentRepository.findByUserIdAndTrainingId(userRepository.findByMail(s).getId(),idTraining) != null))
                throw new MissingDataException("User does not exist");
            else {
                Enrollment enrollment = new Enrollment();
                enrollment.setStatus(EnrollmentStatusType.PENDING);
                enrollment.setTraining(trainingRepository.findById(idTraining).get());
                enrollment.setUser(userRepository.findByMail(s));
                enrollmentRepository.save(enrollment);
            }
        }
    }

    public void saveSubordinatesStatus(String emailUser, Long idTraining, Long status) throws MissingDataException {

        if(emailUser == null || idTraining == null || status == null) {
            throw new MissingDataException("Email, status or id is null");
        }

        User user = userRepository.findByMail(emailUser);

        if(user == null)
            throw new MissingDataException("User does not exist");

        Enrollment enrollment = enrollmentRepository.findByUserIdAndTrainingId(user.getId(), idTraining);

        if(enrollment == null) {
            throw new MissingDataException("Email does not exist!");
        }

        if (status == 1) {
            enrollment.setStatus(EnrollmentStatusType.ACCEPTED);
            enrollmentRepository.save(enrollment);
        }

        if (status == 0)
            enrollmentRepository.delete(enrollment);
    }

    public UserDto getUserData (EmailDto emailDto) {
        UserDtoTransformer userDtoTransformer = new UserDtoTransformer();
        return  userDtoTransformer.transform(userRepository.findByMail(emailDto.getEmail()));
    }

    // for test only
    public void addUser() {
        User user = new User();
        user.setName("Vasile");
        user.setMail("vasile2@gmail.com");
        user.setType(UserType.MANAGER);
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        user.setEnabled(true);
        user.setId(111111113L);
        user.setManager(null);
        user.setLastPasswordResetDate(new Date());
        userRepository.saveAndFlush(user);
    }
}
