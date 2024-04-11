package com.ashish.planservice.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "app_payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;

    private UUID userId;
    private int amount;
}
