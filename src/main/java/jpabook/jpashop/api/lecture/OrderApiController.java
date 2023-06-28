package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.Status;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    @GetMapping("/api/v2/simple-orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderService.findAll();
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
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
