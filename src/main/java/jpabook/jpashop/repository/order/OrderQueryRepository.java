package jpabook.jpashop.repository.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDto(){
        List<OrderQueryDto> orderQueryDtos = em.createQuery(
                "select new jpabook.jpashop.repository.order." +
                        "OrderQueryDto(o.id,m.name,o.orderDate,o.orderStatus,d.address)" +
                " from Order o join o.member m join o.delivery d join o.orderItems oi",OrderQueryDto.class)
                .getResultList();
        List<OrderQueryDto> result = new ArrayList<>();
        for(OrderQueryDto order : orderQueryDtos){
            order.setOrderItems(findOrderItemQueryDto(order.getOrderId()));
            result.add(order);
        }
        return result;
    }
    public List<OrderItemQueryDto> findOrderItemQueryDto(Long orderId){
        return em.createQuery("select new jpabook.jpashop.repository.order." +
                "OrderItemQueryDto(o.id,i.name,oi.orderPrice,oi.count) from OrderItem oi join oi.order o join oi.item i " +
                "where o.id =: orderId",OrderItemQueryDto.class)
                .setParameter("orderId",orderId).getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtoV5(){
        List<OrderQueryDto> orderQueryDtos = em.createQuery(
                        "select new jpabook.jpashop.repository.order." +
                                "OrderQueryDto(o.id,m.name,o.orderDate,o.orderStatus,d.address)" +
                                " from Order o join o.member m join o.delivery d join o.orderItems oi",OrderQueryDto.class)
                .getResultList();
        List<Long> orderIds = new ArrayList<>();
        for(OrderQueryDto dto : orderQueryDtos){
            orderIds.add(dto.getOrderId());
        }
        Map<Long,List<OrderItemQueryDto>> orderQueriesMap =findAllOrderItemQueryDto(orderIds);
        for(OrderQueryDto dto : orderQueryDtos){
            dto.setOrderItems(orderQueriesMap.get(dto.getOrderId()));
        }
        return orderQueryDtos;
    }
    public Map<Long,List<OrderItemQueryDto>> findAllOrderItemQueryDto(List<Long> orderIds){
        List<OrderItemQueryDto> orderItemQueryDtos
                = em.createQuery("select new jpabook.jpashop.repository.order." +
                        "OrderItemQueryDto(o.id,i.name,oi.orderPrice,oi.count) " +
                "from OrderItem oi join oi.item i join oi.order o where o.id in :orderIds",OrderItemQueryDto.class)
                .setParameter("orderIds",orderIds).getResultList();
        Map<Long,List<OrderItemQueryDto>> result = new HashMap<>();
        for(OrderItemQueryDto dto : orderItemQueryDtos){
            result.put(dto.getOrderId(),new ArrayList<>());
        }
        for(OrderItemQueryDto dto : orderItemQueryDtos){
            List<OrderItemQueryDto> dtos = result.get(dto.getOrderId());
            dtos.add(dto);
        }
        return result;
    }
}
