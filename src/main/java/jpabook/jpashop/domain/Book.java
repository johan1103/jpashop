package jpabook.jpashop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@DiscriminatorValue("B")
@Getter
@Entity
public class Book extends Item{
    private String author;
    private String isbn;
}
