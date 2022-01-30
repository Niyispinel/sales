package com.sales.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Product extends CoreEntity{

    private long orderId;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private int quantity;

}