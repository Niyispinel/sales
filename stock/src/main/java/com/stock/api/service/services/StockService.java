package com.stock.api.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stock.api.core.dto.StockRequestDto;
import com.stock.api.core.dto.StockResponseDto;
import com.stock.api.core.model.Stock;
import com.stock.api.service.repositories.StockRepository;
import com.stock.api.utils.exception.ConflictException;
import com.stock.api.utils.exception.CustomResponseCode;
import com.stock.api.utils.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings("ALL")
@Slf4j
@Service
public class StockService {


    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private StockRepository stockRepository;

    public StockService(ModelMapper mapper, ObjectMapper objectMapper, StockRepository stockRepository) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.stockRepository = stockRepository;
    }

    /** <summary>
     * stock creation
     * </summary>
     * <remarks>this method is responsible for creation of new stock</remarks>
     */

    public StockResponseDto createStock(StockRequestDto request) {
        Stock stock = mapper.map(request,Stock.class);
        Stock stockExist = stockRepository.findStockByName(request.getName());
        if(stockExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " stock already exist");
        }
        stock = stockRepository.save(stock);
        log.debug("Create new Stock - {}"+ new Gson().toJson(stock));
        return mapper.map(stock, StockResponseDto.class);
    }


    /** <summary>
     * stock update
     * </summary>
     * <remarks>this method is responsible for updating already existing stock</remarks>
     */

    public StockResponseDto updateStock(StockRequestDto request) {
        Stock stock = stockRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested stock Id does not exist!"));
        mapper.map(request, stock);
        stockRepository.save(stock);
        log.debug("State record updated - {}"+ new Gson().toJson(stock));
        return mapper.map(stock, StockResponseDto.class);
    }

    /** <summary>
     * stock delete
     * </summary>
     * <remarks>this method is responsible for deleting already existing stock</remarks>
     */

    public StockResponseDto deleteStock(Long id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested stock Id does not exist!"));
        stockRepository.deleteById(stock.getId());
        log.debug("Stock Deleted - {}"+ new Gson().toJson(stock));
        return mapper.map(stock, StockResponseDto.class);
    }


    /** <summary>
     * Find stock
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public StockResponseDto findStock(Long id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested stock Id does not exist!"));
        return mapper.map(stock,StockResponseDto.class);
    }



    /** <summary>
     * List of Stocks
     * </summary>
     * <remarks>this method is responsible for getting List of Stocks</remarks>
     */
    public List<Stock> getAll(){
        List<Stock> stocks = stockRepository.findAll();
        return stocks;

    }
}
