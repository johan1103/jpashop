package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    private DeliveryStatus status;
}
