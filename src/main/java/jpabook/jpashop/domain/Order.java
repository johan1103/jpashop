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
    private Status orderStatus;

    //==연관관계 편의 메서드==//
    public void setMember(Member member){
        this.member=member;
    }

    public void addOrderItems(OrderItem orderItem){
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }
    private void setOrderDate(){
        this.orderDate=LocalDateTime.now();
    }
    private void setOrderStatus(Status status){
        this.orderStatus=status;
    }
    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
    }

    //==생성 메서드==//
    public Order(Member member, Delivery delivery,OrderItem ... orderItems){
        this.setMember(member);
        this.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            this.addOrderItems(orderItem);
        }
        this.setOrderDate();
        this.setOrderStatus(Status.ORDER);
    }

    /**
     * 파라미터 없는 생성자 외부 사용 차단
     */
    protected Order(){
    }

    //== 비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(Status.CANCEL);
        for(OrderItem orderItem : this.orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
