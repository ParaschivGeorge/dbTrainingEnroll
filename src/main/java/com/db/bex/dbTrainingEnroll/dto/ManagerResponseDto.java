package com.db.bex.dbTrainingEnroll.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class ManagerResponseDto {
    private Long trainingId;
    private List<String> emails;
}
