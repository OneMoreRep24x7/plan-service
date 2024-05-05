package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UpdateDailyDietReq extends DailyDietReqParams{
    private Long dailyDietId;
}
