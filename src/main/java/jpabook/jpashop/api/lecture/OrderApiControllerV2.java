package jpabook.jpashop.api.lecture;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Status;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.orderSimpleQuery.OrderSimpleQuery;
import jpabook.jpashop.repository.orderSimpleQuery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiControllerV2 {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    @GetMapping("/api/v3/simple-orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> orderDtos = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return orderDtos;
    }
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQuery> ordersV4(){
        List<OrderSimpleQuery> orderSimpleQueries = orderSimpleQueryRepository.findOrderDtos();
        return orderSimpleQueries;
    }

    @Data
    public static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Status orderStatus;
        private Address address;
        public OrderDto(Order order){
            orderId=order.getId();
            name=order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getOrderStatus();
            address=order.getDelivery().getAddress();
        }
        public OrderDto(){

        }
    };
}
