package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertedTrainingDto {
    private String name;
    private Date startDate;
    private Date endDate;
    private Long responsibleId;
    private String vendor;
    private Long nrMax;
    private Long nrMin;
    private TrainingCategoryType categoryType;
    private String technology;
}
