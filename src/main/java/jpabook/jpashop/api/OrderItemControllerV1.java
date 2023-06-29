package jpabook.jpashop.api;

import jpabook.jpashop.api.domain.order.OrderFilterRequest;
import jpabook.jpashop.api.domain.order.OrderListResponse;
import jpabook.jpashop.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemControllerV1 implements OrderItemController {

  private final OrderService orderService;

  @Override
  public OrderListResponse orders(OrderFilterRequest filterRequest) {
    return orderService.searchOrders()
  }
}
