package com.sales.api.service.repositories;


import com.sales.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);

    List<Product> findByOrderId(Long orderId);

}
