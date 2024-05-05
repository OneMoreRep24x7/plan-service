package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MsgDTO {
    private String userFullName;
    private String trainerFullName;
    private String planName;
    private LocalDate planStart;
    private LocalDate planExpire;
    private String userEmail;
    private String phoneNumber;
}
