package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne
    private Delivery delivery;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Status OrderStatus;

    //==연관관계 편의 메서드==//
    public void setMember(Member member){
        this.member=member;
    }

    public void addOrderItems(OrderItem orderItem){
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
    }
}
