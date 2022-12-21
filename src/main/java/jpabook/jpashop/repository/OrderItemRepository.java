package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(OrderItem orderItem){
        em.persist(orderItem);
    }
}
