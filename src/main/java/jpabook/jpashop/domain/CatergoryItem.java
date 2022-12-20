package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.compositekey.CategoryItemPk;
import lombok.Getter;

@Entity
@Getter
public class CatergoryItem {
    @EmbeddedId
    private CategoryItemPk categoryItemPk;
}
