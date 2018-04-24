package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String mail;
    private UserType userType;
    private String lastLoginDate;
}
