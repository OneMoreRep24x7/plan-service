package com.ashish.planservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class DietPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String planName;
    private LocalDate startDate;
    @Column(name = "`repeat`") // Escaping the column name using backticks
    private int repeat;
    private UUID userId;
    private UUID trainerId;
    private LocalDate planExpire;
    @OneToMany()
    @JoinColumn(name = "diet_plan_id")
    List<DailyDiet> dailyDiets = new ArrayList<>();

}
