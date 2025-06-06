package com.ecommerce.OrderService.service;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.exception.CustomException;
import com.ecommerce.OrderService.external.client.PaymentService;
import com.ecommerce.OrderService.external.client.ProductService;
import com.ecommerce.OrderService.external.request.PaymentRequest;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;


    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Product service call");
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("placing order reuquest {}",orderRequest);
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .build();

        Order orderId = orderRepository.save(order);

        log.info("calling payment service");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderId.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getAmount())
                .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment Done Successfully:{}",paymentRequest);
            orderStatus = "PLACED";
        }catch (Exception e){
            log.error("Error Occured in payment. Changing order status");
            orderStatus="PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order place successfully id;{}",orderId.getId());
        return orderId.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException("Provide valid order id","ORDER_ID_NOT_FOUND",404));

        log.info("Fetch product detail invoking product service for product id:{}",order.getProductId());

        OrderResponse.ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/"+order.getProductId(), OrderResponse.ProductResponse.class);

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderId)
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .productResponse(productResponse)
                .build();
        return orderResponse;
    }
}
