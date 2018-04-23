package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.TrainingCategoryType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class TrainingDto {
    private Long id;
    private String name;
    private String date;
    private String duration;
    private String technology;
    private TrainingCategoryType categoryType;
    private String acceptedUsers;
}
