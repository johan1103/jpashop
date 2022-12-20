package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Member {
    @GeneratedValue
    @Id @Column(name = "member_id")
    private Long id;

    private String name;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public void addOrder(Order order){
        order.setMember(this);
        orders.add(order);
    }


}
