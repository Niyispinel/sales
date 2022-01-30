package com.sales.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
public class ProductRequestDto {

    private Long id;
    private String name;
    private String description;

    @NotNull(message = "orderId can not be blank")
    @Min(message = "orderId can not be less than 1", value = 1)
    private Long orderId;

    @NotNull(message = "quantity can not be empty")
    @Min(message = "quantity can not be less than 0", value = 1)
    private Integer quantity;

    @NotNull(message = "unitPrice can not be null")
    @DecimalMin(value = "0.0", message = "unitPrice Price can not be less than 0")
    private BigDecimal unitPrice;

    @NotNull(message = "totalPrice can not be null")
    @DecimalMin(value = "0.0", message = "totalPrice can not be less than 0")
    private BigDecimal totalPrice;

}
