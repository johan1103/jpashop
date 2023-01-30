package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    private DeliveryStatus status;

    public static Delivery createDelivery(Address address,DeliveryStatus deliveryStatus){
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setStatus(deliveryStatus);
        return delivery;
    }
    protected Delivery(){
    }

    public void setOrder(Order order){
        order.setDelivery(this);
        //this.order=order;
    }
    public void setAddress(Address address){
        this.address=address;
    }
    public void setStatus(DeliveryStatus deliveryStatus){
        this.status=deliveryStatus;
    }
}
