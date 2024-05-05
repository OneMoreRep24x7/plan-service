package com.ashish.planservice.dto;

import com.ashish.planservice.model.Tracking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FoodTrackingDTO {
    private String foodName;
    private Tracking details;
    private String message;
    private int statusCode;

}
