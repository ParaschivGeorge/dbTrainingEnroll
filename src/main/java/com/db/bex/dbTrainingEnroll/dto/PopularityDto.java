package com.db.bex.dbTrainingEnroll.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class PopularityDto {

    private String[] technologies;
    private Integer[] noAttendees;
}
