package com.sales.api.utils;


import com.sales.api.dto.OrderRequestDto;
import com.sales.api.dto.ProductRequestDto;
import com.sales.api.service.repositories.OrderRepository;
import com.sales.api.service.repositories.ProductRepository;
import com.sales.api.utils.exception.BadRequestException;
import com.sales.api.utils.exception.CustomResponseCode;
import com.sales.api.utils.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@SuppressWarnings("All")
@Slf4j
@Service
public class Validations {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Validations() {
    }

    public String generateReferenceNumber(int numOfDigits) {
        if (numOfDigits < 1) {
            throw new IllegalArgumentException(numOfDigits + ": Number must be equal or greater than 1");
        }
        long random = (long) Math.floor(Math.random() * 9 * (long) Math.pow(10, numOfDigits - 1)) + (long) Math.pow(10, numOfDigits - 1);
        return Long.toString(random);
    }

    public String generateCode(String code) {
        String encodedString = Base64.getEncoder().encodeToString(code.getBytes());
        return encodedString;
    }

    public void validateOrder (OrderRequestDto request){

        if (request.getStatus() == null || request.getStatus().isEmpty() )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Delivery Status cannot be empty");

        if (request.getCustomerName() == null || request.getCustomerName().isEmpty() )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Customer Name cannot be empty");

        if (request.getCustomerPhone() == null || request.getCustomerPhone().isEmpty() )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Customer Phone cannot be empty");
        if (!Utility.validatePhoneNumber(request.getCustomerPhone().toString()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for Customer Phone ");

        if (request.getDeliveryAddress() == null || request.getDeliveryAddress().isEmpty() )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Delivery Address cannot be empty");

    }

    public void validateProduct (ProductRequestDto request){

        if (!Utility.isNumeric(request.getOrderId().toString()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for orderId ");

        if (request.getName() == null || request.getName().isEmpty() )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");

        if (request.getQuantity() == null )
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Quantity cannot be empty");
        if (!Utility.isNumeric(request.getQuantity().toString()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for Quantity");


        orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " orderId does not Exist!")
        );
    }


}


