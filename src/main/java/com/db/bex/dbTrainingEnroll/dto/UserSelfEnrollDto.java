package com.db.bex.dbTrainingEnroll.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class UserSelfEnrollDto {
    private Long trainingId;
    private String userEmail;
}
