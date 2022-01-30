package com.sales.api.dto;

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
public class ProductResponseDto {

    private Long id;
    private Long orderId;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
