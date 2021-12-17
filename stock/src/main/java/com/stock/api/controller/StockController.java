package com.stock.api.controller;


import com.stock.api.core.dto.Response;
import com.stock.api.core.dto.StockRequestDto;
import com.stock.api.core.dto.StockResponseDto;
import com.stock.api.core.model.Stock;
import com.stock.api.service.services.StockService;
import com.stock.api.utils.exception.CustomResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    /** <summary>
     * stock creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new stock</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createStock(@Validated @RequestBody StockRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        StockResponseDto response = service.createStock(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * stock update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating stock</remarks>
     */

    @PutMapping("/1")
    public ResponseEntity<Response> updateStock(@Validated @RequestBody StockRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        StockResponseDto response = service.updateStock(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * stock delete endpoint
     * </summary>
     * <remarks>this endpoint is responsible for deleting stock</remarks>
     */

    @GetMapping("/1/{id}")
    public ResponseEntity<Response> deleteWarehouseUserById(@PathVariable long id){

        HttpStatus httpCode ;
        Response resp = new Response();
        StockResponseDto response = service.deleteStock(id);
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
    @GetMapping("/1/{id}")
    public ResponseEntity<Response> getStock(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        StockResponseDto response = service.findStock(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * stock list endpoint
     * </summary>
     * <remarks>this endpoint is responsible for list of stocks</remarks>
     */

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Stock> response = service.getAll();
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
