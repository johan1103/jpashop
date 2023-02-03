package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.Status;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.OrderQueryDto;
import jpabook.jpashop.repository.order.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiControllerV3 {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * V2
     * 엔티티 조회 후 DTO로 변환
     * 치명적 단점 : N+1
     * orders를 가져오는데 1Q(query), 순회하면서 OrderItem가져오는데 NQ, 모든 OrderItem에서 Item을 DTO로 변환해서
     * 가져오기 위해 1Q
     * 쿼리가 너무 많이 나간다.
     * @return
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = orders.stream().map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return orderDtos;
    }

    /**
     * V3
     * 엔티티 조회 후 DTO로 변환, JPQL로 fetch join해 가져오기
     * 치명적 단점 : 페이징 불가
     * 페이징은 쿼리문을 날릴 때 매우 중요한 요소임. 긁어오는 Row가 10000단위 이면 Out of Memory가 발생하기 때문.
     * 그래서 페이징으로 적당히 잘라 와야 하는데, List를 요소로 가지는 엔티티는 긁어 올 때 근본적으로 페이징이 불가능하다.
     * 왜? Join의 원리를 이해하면, 정보가 더 많은 쪽 (일 대 다에서 다 쪽)의 Row 수만큼 조인의 결과물이 나오기 때문에 가져오고자 하는
     * 주인의 Row가 중복되어서 나온다.
     * 결국 페이징 해주기 위해 disdinct 키워드를 사용하지만, 그것도 결국 in 쿼리문 사용없이 DB에서 가져와서 메모리에서 처리해주는 과정이기
     * 때문에 Out of Memory를 피할 수 없다.
     * @return
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithOrderItem();
        List<OrderDto> orderDtos = orders.stream().map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return orderDtos;
    }

    /**
     * 내가 생각하는 최선의 방식 (코드 복잡성도 낮고, 쿼리문도 나름 최적화 된 방식)
     * @BatchSize로 프록시를 List로 가져왔을 때에는 반복문에서 루프횟수만큼 LazyLoading을 하지 않고
     * BatchSize혹은 application.properties의 spring.jpa.properties.hibernate.default_batch_fetch_size 만큼
     * 한번에 가져와서 영속성 컨텍스트에 영속시키는 방식
     * (대신 ToOne관계의 엔티티는 FetchJoin해도 상관이 없다. 쿼리가 뻥튀기 되지 않아서 페이징이 가능하기 때문에)
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_1(@RequestParam(value = "offset",defaultValue = "0")int offset,
                                     @RequestParam(value = "limit",defaultValue = "10")int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> result = new ArrayList<>();
        int idx=0;
        for(Order o : orders) {
            System.out.println("idx : " + idx);
            result.add(new OrderDto(o));
            idx+=1;
        }
        return result;
    }

    /**
     * 정보를 JPQL에서 직접 가져오는 방식
     * Fetch Join을 사용하지 못하기 때문에 Id를 가져와서, 한번더 루프를 돌면서 엔티티를 빼오는 방식
     * 두번의 루프를 돌기 때문에 N+1 발생한다.
     * @return
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDto();
    }

    /**
     * JPQL에서 정보를 가져오고,
     * 루프를 돌면서 내부의 OrderItem을 하나씩 빼 오는것이 아닌,
     * OrderItem id를 전부 모아서 한번에 가져온 후, 처음 가져온 OrderDto들이 가지고 있는 OrderItemDto에 직접 넣어주는 방식
     * @return
     */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findOrderQueryDtoV5();
    }


    /**
     * 김영한님 강의 참고 사항
     * 참고: 엔티티 조회 방식은 페치 조인이나, hibernate.default_batch_fetch_size , @BatchSize 같이
     * 코드를 거의 수정하지 않고, 옵션만 약간 변경해서, 다양한 성능 최적화를 시도할 수 있다. 반면에 DTO를
     * 직접 조회하는 방식은 성능을 최적화 하거나 성능 최적화 방식을 변경할 때 많은 코드를 변경해야 한다.
     */


    @Data
    public static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Status orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order){
            orderId=order.getId();
            name=order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getOrderStatus();
            address=order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
        public OrderDto(){

        }
    };
    @Data
    public static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName=orderItem.getItem().getName();
            this.orderPrice=orderItem.getOrderPrice();
            this.count=orderItem.getCount();
        }
    }
}
