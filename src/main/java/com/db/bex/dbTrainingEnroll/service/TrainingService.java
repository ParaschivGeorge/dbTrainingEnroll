package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import com.db.bex.dbTrainingEnroll.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService {

    private TrainingDtoTransformer trainingDtoTransformer;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;

    public List<TrainingDto> findPendingTrainings(EmailDto email) {

        if(email == null) {
            throw new NullPointerException("Email is null");
        }

        User user = userRepository.findByMail(email.getEmail());
        if (user == null)
            return null;
        Long id = user.getId();
        if(id == null) {
            throw new NullPointerException("Email does not exist");
        }
        return trainingDtoTransformer.getTrainings(enrollmentRepository.findTrainingsThatHavePendingParticipants(id));
    }

    public List<TrainingDto> findTrainings() {
        List<Training> trainingList = trainingRepository.findAll();
        return dateSetter(trainingList);
    }

    public Integer countAcceptedUsers(Long idTraining) {
        return enrollmentRepository.countAcceptedUsers(idTraining);
    }

    public Integer[] countAcceptedTrainings() {
        Integer acceptedTech = trainingRepository.countAcceptedTechTraining();
        Integer acceptedSoft = trainingRepository.countAcceptedSoftTrainings();
        Integer[] techReport = {acceptedTech, acceptedSoft};
        return techReport;
    }

    public List<PopularityDto> countTopTechnicalAttendees() {
        return trainingRepository.countAcceptedTrainingsForEachCategory(TrainingCategoryType.TECHNICAL);
    }

    public List<PopularityDto> countTopSoftAttendees() {
        return trainingRepository.countAcceptedTrainingsForEachCategory(TrainingCategoryType.SOFT);
    }

    public List<PopularityDto> countTopAllAttendees() {
        return trainingRepository.countAcceptedTrainingsForAllCategories();
    }

    public List<MonthlyReportDto> findMonthlyReport() {
        List<MonthlyReportDto> list = trainingRepository.getMonthlyReport();
        for (MonthlyReportDto report : list) {
            Integer month = Integer.parseInt(report.getMonthNumber());
            report.setMonthString(getMonthName(month));
        }
        return list;
    }

    private String getMonthName(Integer month) {
        LocalDate localDate = LocalDate.of(1990, month, 1);
        return localDate.getMonth().name();
    }

    private List<TrainingDto> dateSetter(List<Training> trainingList) {
        List<TrainingDto> trainingDtoList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm");
        TrainingDto trainingDto;

        for(Training training : trainingList) {
            trainingDto = trainingDtoTransformer.transform(training);

            String reportDateStart = dateFormat.format(training.getStartDate());
            String reportDateEnd = dateFormat.format(training.getEndDate());

            String reportStartHour = hourFormat.format(training.getStartDate());
            String reportEndHour = hourFormat.format(training.getEndDate());

            String[] startHourParts = reportStartHour.split(":");
            String startHour = startHourParts[0];
            String startMinute = startHourParts[1];

            String[] endHourParts = reportEndHour.split(":");
            String endHour = endHourParts[0];
            String endMinute = endHourParts[1];

            String[] startParts = reportDateStart.split("/");
            String startDay = startParts[0];
            String startMonth = startParts[1];
            String startYear = startParts[2];

            String[] endParts = reportDateEnd.split("/");
            String endDay = endParts[0];
            String endMonth = endParts[1];
            String endYear = endParts[2];

            if (Integer.parseInt(endYear) - Integer.parseInt(startYear) == 0) {
                if (Integer.parseInt(endMonth) - Integer.parseInt(startMonth) == 0) {
                    if (Integer.parseInt(endDay) - Integer.parseInt(startDay) == 0) {
                        trainingDto.setDate(reportDateStart);
                        if (Integer.parseInt(endMinute) - Integer.parseInt(startMinute) >= 0) {
                            int hours = Integer.parseInt(endHour) - Integer.parseInt(startHour);
                            int minutes = Integer.parseInt(endMinute) - Integer.parseInt(startMinute);
                            trainingDto.setDuration(hours + "h " +
                                    minutes + "m");
                        } else {
                            int hours = Integer.parseInt(endHour) - Integer.parseInt(startHour) - 1;
                            int minutes = 60 + Integer.parseInt(endMinute) - Integer.parseInt(startMinute);
                            trainingDto.setDuration(hours + "h " +
                                    minutes + "m");
                        }
                    } else {
                        trainingDto.setDate(reportDateStart + " - " + reportDateEnd);
                        trainingDto.setDuration("-1");
                    }
                } else {
                    trainingDto.setDate(reportDateStart + " - " + reportDateEnd);
                    trainingDto.setDuration("-1");
                }
            } else {
                trainingDto.setDate(reportDateStart + " - " + reportDateEnd);
                trainingDto.setDuration("-1");
            }

            trainingDtoList.add(trainingDto);
        }

        return trainingDtoList;
    }

    public List<TrainingDto> getAllApprovedTrainings(String userEmail) {
        return trainingDtoTransformer.getTrainings(
                    enrollmentRepository.findAllByUserId(
                            userRepository.findByMail(userEmail).getId()));
    }
}
