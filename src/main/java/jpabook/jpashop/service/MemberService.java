package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입 조건
     * 중복 이름 불가
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member.getName());
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public Long update(Long id,String name){
        Member findMember = memberRepository.findOne(id);
        validateDuplicateMember(name);
        findMember.updateName(name);
        return  findMember.getId();
    }


    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(String name){
        List<Member> memberList = memberRepository.findByName(name);
        if(!memberList.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
