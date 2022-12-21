package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    /**
     * 주문
     */
    public Long order(Long memberId,Long itemId,int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        orderItemRepository.save(orderItem);

        //Order 생성
        Order order = Order.createOrder(member,delivery,orderItem);
        return order.getId();
    }
}
