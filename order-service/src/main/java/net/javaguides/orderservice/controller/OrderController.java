package net.javaguides.orderservice.controller;

import net.javaguides.basedomains.DTO.Order;
import net.javaguides.basedomains.DTO.OrderEvent;
import net.javaguides.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();

        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is pending status");

        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully...";
    }
}
