package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@DiscriminatorValue("B")
@Getter
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}
