package jpabook.jpashop.repository.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}
