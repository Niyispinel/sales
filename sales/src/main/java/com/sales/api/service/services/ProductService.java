package com.sales.api.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sales.api.dto.ProductRequestDto;
import com.sales.api.dto.ProductResponseDto;
import com.sales.api.model.Product;
import com.sales.api.service.repositories.OrderRepository;
import com.sales.api.service.repositories.ProductRepository;
import com.sales.api.utils.Validations;
import com.sales.api.utils.exception.ConflictException;
import com.sales.api.utils.exception.CustomResponseCode;
import com.sales.api.utils.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
@Slf4j
@Service
public class ProductService {


    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Validations validations;

    public ProductService(ModelMapper mapper, ObjectMapper objectMapper, ProductRepository productRepository) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
    }

    /** <summary>
     * product creation
     * </summary>
     * <remarks>this method is responsible for creation of new product</remarks>
     */

    public ProductResponseDto createProduct(ProductRequestDto request) {
        validations.validateProduct(request);
        Product product = mapper.map(request, Product.class);
        Product productExist = productRepository.findProductByName(request.getName());
        if(productExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " product already exist");
        }
        product.setIsActive(true);
        product = productRepository.save(product);
        log.debug("Create new Product - {}"+ new Gson().toJson(product));
        return mapper.map(product, ProductResponseDto.class);
    }

    public  List<Product> createProducts(List<ProductRequestDto> requests) {
        List<Product> responseDtos = new ArrayList<>();
        requests.forEach(request->{
            validations.validateProduct(request);
            Product product = mapper.map(request, Product.class);
            Product productExist = productRepository.findProductByName(request.getName());
            if(productExist !=null){
                throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " product already exist");
            }
            product.setIsActive(true);
            product = productRepository.save(product);
            log.debug("Create new Product - {}"+ new Gson().toJson(product));
            responseDtos.add(mapper.map(product, Product.class));
        });
        return responseDtos;
    }


    /** <summary>
     * product update
     * </summary>
     * <remarks>this method is responsible for updating already existing product</remarks>
     */

    public ProductResponseDto updateProduct(ProductRequestDto request) {
        validations.validateProduct(request);
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested product Id does not exist!"));
        mapper.map(request, product);
        productRepository.save(product);
        log.debug("State record updated - {}"+ new Gson().toJson(product));
        return mapper.map(product, ProductResponseDto.class);
    }

    /** <summary>
     * product delete
     * </summary>
     * <remarks>this method is responsible for deleting already existing product</remarks>
     */

    public ProductResponseDto deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested product Id does not exist!"));
        productRepository.deleteById(product.getId());
        log.debug("Product Deleted - {}"+ new Gson().toJson(product));
        return mapper.map(product, ProductResponseDto.class);
    }


    /** <summary>
     * Find product
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public ProductResponseDto findProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested product Id does not exist!"));
        return mapper.map(product, ProductResponseDto.class);

    }



    /** <summary>
     * List of Products
     * </summary>
     * <remarks>this method is responsible for getting List of Products</remarks>
     */
    public List<Product> getAll(){
        List<Product> products = productRepository.findAll();
        return products;

    }
}
