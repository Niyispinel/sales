package com.sales.api.service.repositories;


import com.sales.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Order findByReferenceNo(String referenceNo);

    Order findOrderById(Long id);

    List<Order> findByIsActive(Boolean isActive);


}