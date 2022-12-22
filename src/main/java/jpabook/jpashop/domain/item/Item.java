package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.CatergoryItem;
import jpabook.jpashop.exception.NotEnoughStockException;
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

    public void setName(String name){
        this.name=name;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setStockQuantity(int quantity){
        this.stockQuantity=quantity;
    }
    //== 비즈니스 로직 추가 ==//

    /**
     * 객체지향의 관점에서 응집도를 낮추는 설계
     * 필드값을 수정하는 주체는 필드값을 소유하고 있는 객체에게 역할 위임하는 것이 응집도를 낮추기에 좋음
     */
    public void AddStock(int quantity){
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int resStock=this.stockQuantity-quantity;
        if(resStock<0){
            throw new NotEnoughStockException ("need more stock");

        }
        this.stockQuantity=resStock;
    }
}
