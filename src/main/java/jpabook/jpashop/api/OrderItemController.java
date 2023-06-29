package jpabook.jpashop.api;

import jpabook.jpashop.api.domain.order.OrderFilterRequest;
import jpabook.jpashop.api.domain.order.OrderListResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public interface OrderItemController {
  @GetMapping("/orderList")
  public OrderListResponse orders(@RequestBody @Validated OrderFilterRequest filterRequest);
}
