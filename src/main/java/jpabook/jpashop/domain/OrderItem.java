package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders_item")
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "orders_item_id")
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Order order;

    private int orderPrice;
    private int count;
}
