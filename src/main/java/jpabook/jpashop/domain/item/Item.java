package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.CatergoryItem;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;
    /*
    @OneToMany(mappedBy = "categoryItemPk")
    private List<CatergoryItem> catergoryItems;

     */
}
