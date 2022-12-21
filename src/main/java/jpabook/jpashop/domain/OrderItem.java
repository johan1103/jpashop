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

    //==생성 로직==//
    public static OrderItem createOrderItem(Item item, int orderPrice,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.item=item;
        orderItem.orderPrice=orderPrice;
        orderItem.count=count;
        return orderItem;
    }
    /**
     * 파라미터 없는 생성자 외부 사용 차단
     */
    protected OrderItem(){
    }

    //==비즈니스 로직==//
    /**
     * 상품 취소
     */
    public void cancel(){
        this.item.AddStock(count);
    }
    /**
     * 상품 총액 계산
     */
    public int getTotalPrice(){
        return orderPrice*count;
    }

}
