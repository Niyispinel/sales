package com.stock.api.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * This class collects the request and map it to the entity class
 */


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StockRequestDto {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Current Price can not be null")
    @DecimalMin(value = "0.0", message = "Current Price can not be less than 0")
    private BigDecimal currentPrice;

}
