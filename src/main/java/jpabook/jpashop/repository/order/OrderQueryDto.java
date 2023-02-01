package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private Status orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name,LocalDateTime orderDate,Status orderStatus,Address address){
        this.orderId=orderId;
        this.name=name;
        this.orderDate=orderDate;
        this.orderStatus=orderStatus;
        this.address=address;
    }
}
