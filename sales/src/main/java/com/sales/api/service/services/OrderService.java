package com.sales.api.service.services;

import com.google.gson.Gson;
import com.sales.api.dto.CreateOrderResponse;
import com.sales.api.dto.OrderRequestDto;
import com.sales.api.dto.OrderResponseDto;
import com.sales.api.model.Order;
import com.sales.api.model.Product;
import com.sales.api.service.repositories.OrderRepository;
import com.sales.api.service.repositories.ProductRepository;
import com.sales.api.utils.API;
import com.sales.api.utils.Validations;
import com.sales.api.utils.exception.ConflictException;
import com.sales.api.utils.exception.CustomResponseCode;
import com.sales.api.utils.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Autowired
    private API api;


    @Value("${create.order}")
    private String processOrder;

    @Value("${finger.print}")
    private String fingerPrint;

    @Autowired
    private Validations validations;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public OrderService(OrderRepository orderRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    public OrderResponseDto createOrder(OrderRequestDto request, HttpServletRequest request1) {
        List<Product> responseDtos = new ArrayList<>();
        validations.validateOrder(request);
        Order order = mapper.map(request,Order.class);

        order.setReferenceNo(validations.generateReferenceNumber(10));
        Order orderExists = orderRepository.findByReferenceNo(order.getReferenceNo());
        if(order.getReferenceNo() == null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Order does not have Reference Number");
        }

        if(orderExists != null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Order already exist");
        }

        order.setBarCode(validations.generateCode(order.getReferenceNo()));
        order.setQrCode(validations.generateCode(order.getReferenceNo()));

        order.setIsActive(true);
        order = orderRepository.save(order);
        log.debug("Create new order - {}"+ new Gson().toJson(order));


        OrderResponseDto orderResponseDto = mapper.map(order, OrderResponseDto.class);

        if(request.getProducts() != null) {
            request.getProducts().forEach(product -> {
                product.setOrderId(orderResponseDto.getId());
            });

            responseDtos = productService.createProducts(request.getProducts());
            List<Product> finalResponseDtos = responseDtos;
            responseDtos.forEach(product -> {
                orderResponseDto.setProducts(finalResponseDtos);
            });
        }

        placeOrder(request);

        return orderResponseDto;
    }

    public OrderResponseDto updateOrder(OrderRequestDto request) {
        validations.validateOrder(request);
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested order Id does not exist!"));
        mapper.map(request, order);

        orderRepository.save(order);
        log.debug("order record updated - {}"+ new Gson().toJson(order));

        OrderResponseDto orderResponseDto = mapper.map(order, OrderResponseDto.class);
        return orderResponseDto;
    }


    public OrderResponseDto findOrder(Long id){
        Order order  = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested order Id does not exist!"));
        OrderResponseDto orderResponseDto = mapper.map(order, OrderResponseDto.class);
        orderResponseDto.setProducts(getProducts(id));

        return orderResponseDto;

    }


    public List<Order> getAll(){
        List<Order> orders = orderRepository.findAll();
        return orders;

    }

    public List<Product> getProducts(Long orderId){
        List<Product> products = productRepository.findByOrderId(orderId);
        return products;

    }

    public CreateOrderResponse placeOrder (OrderRequestDto request) {

        Map map=new HashMap();
        map.put("fingerprint",fingerPrint);
        Order order = mapper.map(request,Order.class);

        CreateOrderResponse response = api.post(processOrder ,order, CreateOrderResponse.class,map);
        if (response.isStatus())
            saveOrder(request);
        return response;

    }

    private void saveOrder(OrderRequestDto request) {

        Order order = mapper.map(request,Order.class);
        log.info("validating order " + request);
        validations.validateOrder(request);
        log.info("::::::::::::ORDER REQUEST::::::::::::::::: " + order);
        orderRepository.save(order);
    }
}
