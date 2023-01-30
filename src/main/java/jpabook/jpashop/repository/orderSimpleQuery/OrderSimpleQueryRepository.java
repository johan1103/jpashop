package jpabook.jpashop.repository.orderSimpleQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    @PersistenceContext
    private EntityManager em;

    public List<OrderSimpleQuery> findOrderDtos(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.orderSimpleQuery" +
                        ".OrderSimpleQuery(o.id,m.name,o.orderDate,o.orderStatus,d.address) " +
                        "from Order o join o.member m join o.delivery d",OrderSimpleQuery.class)
                .getResultList();
    }
}
