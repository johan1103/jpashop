package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Delivery;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Delivery delivery){
        em.persist(delivery);
    }
}
