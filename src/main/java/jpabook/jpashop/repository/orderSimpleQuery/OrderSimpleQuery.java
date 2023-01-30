package jpabook.jpashop.repository.orderSimpleQuery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQuery {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Status orderStatus;
        private Address address;
        public OrderSimpleQuery(Long orderId,String name,LocalDateTime orderDate,Status orderStatus, Address address){
            this.orderId=orderId;
            this.name=name;
            this.orderDate=orderDate;
            this.orderStatus=orderStatus;
            this.address=address;
        }
        public OrderSimpleQuery(){

        }
}
