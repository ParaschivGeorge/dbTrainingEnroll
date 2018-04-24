package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.*;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDtoTransformer userDtoTransformer;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;
    private EmailService emailService;
    @Autowired
    private TrainingDtoTransformer trainingDtoTransformer;

    public UserService(UserRepository userRepository, UserDtoTransformer userDtoTransformer,
                       EnrollmentRepository enrollmentRepository, TrainingRepository trainingRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userDtoTransformer = userDtoTransformer;
        this.enrollmentRepository = enrollmentRepository;
        this.trainingRepository = trainingRepository;
        this.emailService = emailService;
    }

    public List<UserDto> findSubordinates(String email, long trainingId){
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = userRepository.findAllByManagerId(userRepository.findByMail(email).getId());
        if (users == null)
            return null;
        System.out.println(users);
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

    public List<UserDto> findPendingUsers(Long idTraining, String email) {

        Long idPm = userRepository.findByMail(email).getId();

        return userDtoTransformer.getUserSubordinates1(userRepository.findPendingUsers(idTraining, idPm));
    }

    public void savePendingSubordinates(Long idTraining, List<String> emails){
        for(String s:emails) {
            if((enrollmentRepository
                    .findByUserIdAndTrainingId(userRepository.findByMail(s).getId(),idTraining) != null))
                return;
            else {
                Enrollment enrollment = new Enrollment();
                enrollment.setStatus(EnrollmentStatusType.PENDING);
                enrollment.setTraining(trainingRepository.findById(idTraining).get());
                enrollment.setUser(userRepository.findByMail(s));
                enrollmentRepository.save(enrollment);
            }
        }
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

    public void saveSubordinatesStatusAndSendEmail(List<UserStatusDto> userStatusDtos){
        List<String> userEmails = new ArrayList<>();
        List<String> managerEmails = new ArrayList<>();
        Long trainingId = userStatusDtos.get(0).getIdTraining();
        for(UserStatusDto u : userStatusDtos) {
            String mailUser = u.getMailUser();
            Long idTraining = u.getIdTraining();
            Long status = u.getStatus();
            Long id = userRepository.findByMail(mailUser).getId();

            Enrollment enrollment = enrollmentRepository.findByUserIdAndTrainingId(id, idTraining);

            if (status == 1) {
                userEmails.add(mailUser);
                String managerMail = userRepository.findByMail(mailUser).getManager().getMail();
                if (!managerEmails.contains(managerMail))
                    managerEmails.add(managerMail);
                enrollment.setStatus(EnrollmentStatusType.ACCEPTED);
                enrollmentRepository.save(enrollment);
            }
            if (status == 0)
                enrollmentRepository.delete(enrollment);
        }
        //TODO: Delete hardcoding
        managerEmails.clear();
        managerEmails.add("stefaneva25@yahoo.com");
        managerEmails.add("stefaneva25@yahoo.com");
        this.sendEmailToSubordinates(userEmails, trainingId);
        this.sendEmailToManagersWithSubordinates(managerEmails,trainingId);
    }

    public void sendEmailToSubordinates(List<String> emails, Long trainingId){
        try {
            emailService.sendEmailToUsers(emails,"Congratulations! \n You've been approved at the training " +  trainingRepository.findById(trainingId).get() + "."
                    ,"Training Approval");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailToManagersWithSubordinates(List<String> managers, Long trainingId){
        try {
            List<String> emails;
            for(String s : managers) {
//                emails = enrollmentRepository.findApprovedSubordinatesAtTrainingId(userRepository.findByMail(s).getId(), trainingId);
//                Mocks for emails
                emails = new ArrayList<>();
                emails.add("stefaneva25@yahoo.com");
                emails.add("asdasdasd@fsssccc.com");
                emails.add("asdasdas@gmail.com");
                emailService.sendEmailToManager(s, "The following: \n\n " +
                        this.emailFormatter(emails) +  " \n have been approved at " +
                        trainingRepository.findById(trainingId).get() + ".", "Subordinates approved at training");
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String emailFormatter(List<String> emails){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < emails.size(); i++) {
            String email = emails.get(i);
            stringBuilder.append("hard coded user");
//            stringBuilder.append(userRepository.findByMail(email).getName());
            stringBuilder.append("(");
            stringBuilder.append(email);
            stringBuilder.append(")");
            if(i != emails.size()-1)
                stringBuilder.append(",");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<TrainingDto> findRecommendedTrainings(List<Long> trainingsId){
        List<Training> trainings = null;
        if(!trainingsId.isEmpty())
        {
            trainings = new ArrayList<>();
            for(Long i : trainingsId)
                trainings.add(trainingRepository.findById(i).get());
        }
        return trainingDtoTransformer.getTrainings(trainings);
    }
}
