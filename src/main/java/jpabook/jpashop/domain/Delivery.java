package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    private DeliveryStatus status;

    public void setOrder(Order order){
        order.setDelivery(this);
        this.order=order;
    }
}
