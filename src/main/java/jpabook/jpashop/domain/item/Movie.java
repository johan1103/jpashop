package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@DiscriminatorValue("M")
@Getter
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
