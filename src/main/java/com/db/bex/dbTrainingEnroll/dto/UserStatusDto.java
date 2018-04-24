package com.db.bex.dbTrainingEnroll.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class UserStatusDto {
    private String mailUser;
    private Long idTraining;
    private Long status;
}
