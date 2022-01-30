package com.sales.api.controller;


import com.sales.api.dto.OrderRequestDto;
import com.sales.api.dto.OrderResponseDto;
import com.sales.api.dto.Response;
import com.sales.api.model.Order;
import com.sales.api.service.services.OrderService;
import com.sales.api.utils.exception.CustomResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@SuppressWarnings("All")
@RestController
@RequestMapping("/api/order")
public class OrderController {


    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }


    /** <summary>
     * Order creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Orders</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createOrder(@Validated @RequestBody OrderRequestDto request, HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        OrderResponseDto response = service.createOrder(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * Order update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating orders</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateOrder(@Validated @RequestBody  OrderRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        OrderResponseDto response = service.updateOrder(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Get single record endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting a single record</remarks>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrder(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        OrderResponseDto response = service.findOrder(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Order> response = service.getAll();
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
