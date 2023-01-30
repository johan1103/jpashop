package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Order order){
        em.persist(order);
    }
    public List<Order> findAll(){return em.createQuery("select o from Order o",Order.class).getResultList();}

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }
    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.orderStatus = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class) .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }
    public List<Order> findAllWithMemberDelivery(){
        return em.createQuery("select o from Order o join fetch o.delivery d" +
                " join fetch o.member m",Order.class).getResultList();
    }

    public List<Order> findAllWithOrderItem(){
        /**
         * HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
         * 발생하면서 DB에는 페이징 쿼리가 날라가지 않고 메모리에서 페이징 처리를 한다.
         */
        return em.createQuery("select o from Order o join fetch o.delivery d " +
                "join fetch o.member m join fetch o.orderItems oi join fetch oi.item i",Order.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
