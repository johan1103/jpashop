package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //데이터를 변경해야하기 때문에 추가(?)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); //db에 강제로 쿼리 날림
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외()throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        /*
        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        } 원래는 이렇게 코드 작성해야 하지만 @Test(expected = IllegalStateException.class)를 해주면
        IllegalStateExceptoin이 발생해서 예외가 났을 때를 테스트 성공이라고 취급하게 된다. 그래서 그냥 밑에 처럼 작성해주면 된다
        */
        memberService.join(member1);
        memberService.join(member2);
        //then
        //코드가 fail을 만나면 잘못된 코드이므로 이 Assert의 fail함수가 페일 메세지를 떨굼굼
        fail("예외가 발생해야한다.");
    }
}