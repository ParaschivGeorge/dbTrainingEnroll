package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.Recommender;
import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.dao.UserRepository;
import com.db.bex.dbTrainingEnroll.dto.*;
import com.db.bex.dbTrainingEnroll.entity.Training;
import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import com.db.bex.dbTrainingEnroll.entity.User;
import com.db.bex.dbTrainingEnroll.exceptions.MissingDataException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService {

    private TrainingDtoTransformer trainingDtoTransformer;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;
    private TrainingRepository trainingRepository;
    @Autowired
    @Qualifier("dataSource1")
    private DataSource dataSource;

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

    public void insertTrainingList(List<InsertedTrainingDto> insertedTrainingDtoList) {
        for(InsertedTrainingDto insertedTrainingDto : insertedTrainingDtoList) {
            Training training = new Training();
            training.setName(insertedTrainingDto.getName());
            training.setTechnology(insertedTrainingDto.getTechnology());
            training.setCategory(insertedTrainingDto.getCategoryType());
            training.setEndDate(insertedTrainingDto.getEndDate());
            training.setStartDate(insertedTrainingDto.getStartDate());
            training.setNrMax(insertedTrainingDto.getNrMax());
            training.setNrMin(insertedTrainingDto.getNrMin());
            training.setTrainingResponsible(userRepository.findById(insertedTrainingDto.getResponsibleId()).get());
            training.setVendor(insertedTrainingDto.getVendor());

            trainingRepository.save(training);
        }
    }

    public void updateTrainingList(List<TrainingDto> trainingDtos) throws MissingDataException {

        for(TrainingDto trainingDto : trainingDtos) {
            long id = trainingDto.getId();

            Training training = trainingRepository.findById(id);

            training.setName(trainingDto.getName());
            training.setTechnology(trainingDto.getTechnology());
            training.setCategory(trainingDto.getCategoryType());
            training.setNrMax(trainingDto.getNrMax());
            training.setNrMin(trainingDto.getNrMin());
            training.setTrainingResponsible(userRepository.findByMail(trainingDto.getTrainingResponsible().getMail()));
            training.setVendor(trainingDto.getVendor());
            dateSetter(trainingDto, training);

            trainingRepository.save(training);

        }
    }

    private void dateSetter(TrainingDto trainingDto, Training training) {
        String endDate, startDate;
        String date = trainingDto.getDate();

        if(date.indexOf('-') >= 0) { //contine n-, deci trebuie sa impartim date in startDate si endDate
            String[] dates = date.split("\\ - ");
            startDate = dates[0];
            endDate = dates[1];
        }
        else {
            startDate = date;
            endDate = date;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date startDate1 = formatter.parse(startDate);
            Date endDate1 = formatter.parse(endDate);
            training.setStartDate(startDate1);
            training.setEndDate(endDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

    public void deleteTrainingList(List<Long> trainingIdList) {
        for (Long trainingId : trainingIdList) {
            trainingRepository.deleteById(trainingId);
        }
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
        return this.dateSetter(enrollmentRepository.findAllByUserId(
                userRepository.findByMail(userEmail).getId()));
    }

    public List<TrainingDto> findRecommendedTrainings(String email){
        Long userId = userRepository.findByMail(email).getId();
        Recommender recommender = new Recommender(trainingRepository,dataSource);
        List<Long> trainingsId = recommender.recommendTraining(userId,4);
        List<Training> trainings = null;
        if(!trainingsId.isEmpty())
        {
            trainings = new ArrayList<>();
            for(Long i : trainingsId)
                trainings.add(trainingRepository.findById(i).get());
        }
        return this.dateSetter(trainings);
    }
}
