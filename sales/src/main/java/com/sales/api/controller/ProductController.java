package com.sales.api.controller;


import com.sales.api.dto.ProductRequestDto;
import com.sales.api.dto.ProductResponseDto;
import com.sales.api.dto.Response;
import com.sales.api.model.Product;
import com.sales.api.service.services.ProductService;
import com.sales.api.utils.exception.CustomResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /** <summary>
     * product creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new product</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createProduct(@Validated @RequestBody ProductRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductResponseDto response = service.createProduct(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * product update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating product</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateProduct(@Validated @RequestBody ProductRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductResponseDto response = service.updateProduct(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * product delete endpoint
     * </summary>
     * <remarks>this endpoint is responsible for deleting product</remarks>
     */

    @GetMapping("/delete/{id}")
    public ResponseEntity<Response> deleteWarehouseUserById(@PathVariable long id){

        HttpStatus httpCode ;
        Response resp = new Response();
        ProductResponseDto response = service.deleteProduct(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record Deleted !");
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
    public ResponseEntity<Response> getProduct(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductResponseDto response = service.findProduct(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * product list endpoint
     * </summary>
     * <remarks>this endpoint is responsible for list of products</remarks>
     */

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Product> response = service.getAll();
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
