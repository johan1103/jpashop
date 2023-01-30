package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.Status;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiControllerV3 {
    private final OrderRepository orderRepository;
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = orders.stream().map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return orderDtos;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithOrderItem();
        List<OrderDto> orderDtos = orders.stream().map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return orderDtos;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_1(@RequestParam(value = "offset",defaultValue = "0")int offset,
                                     @RequestParam(value = "limit",defaultValue = "10")int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> result = new ArrayList<>();
        int idx=0;
        for(Order o : orders) {
            System.out.println("idx : " + idx);
            result.add(new OrderDto(o));
            idx+=1;
        }
        return result;
    }


    @Data
    public static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Status orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order){
            orderId=order.getId();
            name=order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getOrderStatus();
            address=order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
        public OrderDto(){

        }
    };
    @Data
    public static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName=orderItem.getItem().getName();
            this.orderPrice=orderItem.getOrderPrice();
            this.count=orderItem.getCount();
        }
    }
}
