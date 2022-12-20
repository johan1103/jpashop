package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders_item")
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "orders_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private int orderPrice;
    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
