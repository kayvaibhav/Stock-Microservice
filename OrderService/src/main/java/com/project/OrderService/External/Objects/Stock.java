package com.project.OrderService.External.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
    private Long stockId;
    private String stockName;
    private Long stockPrice;
    private Long stockQuantity;
    private Instant stockTime;
}
