package jpabook.jpashop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@DiscriminatorValue("M")
@Getter
@Entity
public class Movie extends Item{
    private String director;
    private String actor;
}
