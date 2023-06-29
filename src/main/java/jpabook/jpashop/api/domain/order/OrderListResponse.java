package jpabook.jpashop.api.domain.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.Order;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Setter
public class OrderListResponse {

  public List<OrderSummary> orders;

  @Schema(description = "다음 페이지 존재 여부", example = "false", defaultValue = "null")
  public boolean hasNext;

  @Schema(description = "현재 페이지", example = "0", defaultValue = "null")
  public Integer page;

  @Schema(description = "한 페이지 크기", example = "3", defaultValue = "null")
  public Integer size;

  public static OrderListResponse ofOrderSummaries(Slice<OrderSummary> summaries){
    OrderListResponse response = new OrderListResponse();
    Pageable pageable = summaries.getPageable();
    response.setPage(pageable.getPageNumber());
    response.setSize(pageable.getPageSize());
    response.setHasNext(summaries.hasNext());
    response.setOrders(summaries.stream().toList());
    return response;
  }
}
