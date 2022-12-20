package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;

    private String etc;
}
