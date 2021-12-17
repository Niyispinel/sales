package com.stock.api.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockResponseDto {

    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;
}
