package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @Embedded
    private Address address;
    //이런식으로 참조를 Member와 Orders가 서로 참조를 하면
    //외래키 변경을 할 때, 하나만 할 수 있도록 해야 하므로 연관관계의 주인에게만 정보를 주는게 맞음
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();
}
