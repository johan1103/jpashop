package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //give
        Member member = new Member();
        member.createMember("kim",null);
        //when
        Long joinMemberId = memberService.join(member);
        //then
        assertEquals(member,memberRepository.findOne(joinMemberId));
    }
    @Test
    public void 중복_회원_조회() throws Exception{
        //given
        Member member1=new Member();
        member1.createMember("joe",null);
        Member member2=new Member();
        member2.createMember("joe",null);
        //when
        memberService.join(member1);
        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다", thrown.getMessage());
    }

}