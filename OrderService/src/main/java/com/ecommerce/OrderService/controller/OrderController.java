package com.ecommerce.OrderService.controller;

import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {

        long orderId = orderService.placeOrder(orderRequest);

        log.info("Order Id: {}",orderId);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable(name = "id") long orderId){

        log.info("Order id:{}",orderId);

        OrderResponse orderResponse = orderService.getOrderDetails(orderId);

        return new ResponseEntity<>(orderResponse,HttpStatus.OK);

    }
}
