package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.dao.EnrollmentRepository;
import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import com.db.bex.dbTrainingEnroll.entity.Training;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TrainingDtoTransformer {

    TrainingRepository trainingRepository;
    EnrollmentRepository enrollmentRepository;

    public List<TrainingDto> getTrainings(List<Training> training){
        return training.stream().map(this::transform).collect(Collectors.toList());
    }

    public TrainingDto transform(Training training){

       TrainingDto trainingDto = TrainingDto.builder()
                .id(training.getId())
                .name(training.getName())
                .technology(training.getTechnology())
                .categoryType(training.getCategory())
                .acceptedUsers(enrollmentRepository.countAcceptedUsers(training.getId()).toString())
                .build();

       dateSetter(training, trainingDto);

       return trainingDto;
    }

    private void dateSetter(Training training, TrainingDto trainingDto) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String reportDateStart = dateFormat.format(training.getStartDate());
        String reportDateEnd = dateFormat.format(training.getEndDate());

        DateFormat hourFormat = new SimpleDateFormat("HH:mm");
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

    }

}
