package jpabook.jpashop.api.domain.order;

import jpabook.jpashop.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderSummary {
  private long orderId;
  private List<ItemSummary> items;
  private String memberName;

  @Getter
  @Setter
  @RequiredArgsConstructor
  @NoArgsConstructor
  public static class ItemSummary{
    private String name;
    private int count;
  }
}
