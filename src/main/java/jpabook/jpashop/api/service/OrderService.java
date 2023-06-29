package jpabook.jpashop.api.service;

import jpabook.jpashop.api.domain.order.OrderListResponse;
import jpabook.jpashop.api.domain.order.OrderSummary;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

  public Slice<OrderSummary> searchOrders();
}
