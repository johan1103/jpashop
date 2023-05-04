package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.log.strategy.LogTemplate;
import jpabook.jpashop.repository.MemberJpaRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final LogTemplate logTemplate;
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

    @Transactional
    public Long jpaJoin(Member member){
        validateDuplicateMember(member.getName());
        memberJpaRepository.save(member);
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
        return logTemplate.execute("findMembers", memberRepository::findAll);
    }
    public Page<Member> findMembersJpa(Integer offset, Integer size){
        PageRequest pageRequest = PageRequest.of(offset, size);
        Page<Member> memberPage = memberJpaRepository.findAllMember(pageRequest);
        return memberPage;
    }
    public Slice<Member> findMembersSliceJpa(Integer offset, Integer size){
        PageRequest pageRequest = PageRequest.of(offset, size);
        Slice<Member> memberSlice = memberJpaRepository.findAllMemberBySlice(pageRequest);
        return memberSlice;
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
