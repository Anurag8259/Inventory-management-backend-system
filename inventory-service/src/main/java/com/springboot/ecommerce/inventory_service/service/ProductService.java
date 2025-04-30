package com.springboot.ecommerce.inventory_service.service;

import com.springboot.ecommerce.inventory_service.dto.OrderRequestDto;
import com.springboot.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.springboot.ecommerce.inventory_service.dto.ProductDto;
import com.springboot.ecommerce.inventory_service.entity.Product;
import com.springboot.ecommerce.inventory_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> getAllInventory(){
        log.info("Fetching all inventory items");
        List<Product> inventories=productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id){
        log.info("Fetching product with Id : {}",id);
        Optional<Product> inventory=productRepository.findById(id);
        return inventory.map(item->modelMapper.map(item,ProductDto.class))
                .orElseThrow(()->new RuntimeException("Inventory not found"));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");
        double totalPrice=0.0;
        for(OrderRequestItemDto orderRequestItemDto:orderRequestDto.getItems()){
            Long productId= orderRequestItemDto.getProductId();
            Integer quantity= orderRequestItemDto.getQuantity();

            Product product=productRepository.findById(productId).orElseThrow(()->
                    new RuntimeException("Product not found with Id : "+productId));

            if(product.getStock()<quantity){
                throw new RuntimeException("Product quantity cannot be fulfilled");
            }

            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice+=quantity*product.getPrice();

        }
        return totalPrice;
    }
}
