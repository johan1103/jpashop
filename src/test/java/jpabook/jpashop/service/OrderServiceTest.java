package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Status;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);
        int orderCount=2;

        //when
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(Status.ORDER,getOrder.getOrderStatus());
        assertEquals(1,getOrder.getOrderItems().size());
        assertEquals(10000 * 2,getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());

    }
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 9; //재고보다 많은 수량
        // When
        orderService.order(member.getId(), item.getId(), orderCount);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () ->orderService.order(member.getId(), item.getId(), orderCount));
        //assertEquals("need more stock", thrown.getMessage());
    }

    @Test
    public void 주문취소() throws Exception{
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(),
                orderCount);
        //When
        orderService.cancelOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(Status.CANCEL,
                getOrder.getOrderStatus());
        assertEquals(10,
                item.getStockQuantity());

    }

    private Member createMember(){
        Member member = new Member();
        member.createMember("회원1",new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}