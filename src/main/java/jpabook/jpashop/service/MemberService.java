package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//데이터 변경할때는 트랜잭션이 있어야함(?) 무슨소린지 잘 모르겠음
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 자동 생성해주는 lombok,
// 생성자 주입 방식을 사용할때 생성자를 따로 만들 수고를 덜어줌
public class MemberService {

    private final MemberRepository memberRepository;

    //1. setter 주입 방식
    //우리가 원하는 리포지토리를 주입할 수 있음 (setter함수이므로, 원할 때 호출가능하므로), testcase할 때 용이함
    //단점 : 중간에 리포지토리가 바뀌는 의미도 없고 바뀔 필요도 없는데, 바뀔 수 있음
    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */
    //2. 생성자 주입 방식
    //testcase실행할 때, 우리가 원하는 임의로 만든 레포지토리를 주입할 수 있음, 단 생성할 때만 가능함,
    //중간에 바뀔 수 있는 setter생성자 주입의 단점을 보완하는 방법
    //최신버전 스프링은 이 코드에 @Autowired 하지 않아도 자동 생성자 주입됨
    //한번 주입하고 다시는 바꾸지 않을 테니 final 키워드 쓰는 것을 추천
    //이것 조차 RequiredArgsConstructor를 사용하면 자동으로 만들어줌
    /*
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */
    //회원 가입
    @Transactional
    public Long join(Member member){
        //중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    //Transactional에 읽는 용도로 readOnly를 사용하면 부하(?)가 덜함, readOnly를 쓰지 않는다면 기본은 false값
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
