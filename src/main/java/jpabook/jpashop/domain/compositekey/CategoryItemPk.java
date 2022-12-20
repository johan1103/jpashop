package jpabook.jpashop.domain.compositekey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import java.io.Serializable;

@Getter
@Embeddable
public class CategoryItemPk implements Serializable {
    @JoinColumn(name = "catergory_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
