package com.springboot.ecommerce.order_service.controller;

import com.springboot.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.springboot.ecommerce.order_service.dto.OrderRequestDto;
import com.springboot.ecommerce.order_service.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@Slf4j
public class OrdersController {

    private final OrdersService ordersService;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public OrdersController(OrdersService ordersService, InventoryOpenFeignClient inventoryOpenFeignClient) {
        this.ordersService = ordersService;
        this.inventoryOpenFeignClient = inventoryOpenFeignClient;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){

        OrderRequestDto orderRequestDto1=ordersService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);

    }


    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello from Orders Service";
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest){
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders=ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(Long id){
        log.info("Fetching order with Id :{} via controller" ,id);
        OrderRequestDto order=ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

}
