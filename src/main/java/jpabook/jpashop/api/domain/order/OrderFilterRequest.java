package jpabook.jpashop.api.domain.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Order 필터 요청 정보")
public class OrderFilterRequest {

  @Schema(description = "총 주문 아이템 개수", example = "10", defaultValue = "0")
  @PositiveOrZero
  @NotNull
  private int totalItemCount;

  @Schema(description = "주문 id 번호", example = "10", defaultValue = "0")
  @PositiveOrZero
  @NotNull
  private int id;
}
