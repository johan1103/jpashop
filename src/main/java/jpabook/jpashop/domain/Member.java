package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    private void setAddress(Address address){
        this.address=address;
    }
    private void setName(String name){
        this.name=name;
    }

    public void createMember(String name,Address address){
        setName(name);
        setAddress(address);
    }

    public void updateName(String name){
        setName(name);
    }

}
