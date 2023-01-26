package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member requestMember){
        Long id = memberService.join(requestMember);
        return new CreateMemberResponse(id);
    }

    /**
     * V2 개선점
     * 메서드 파라미터를 해당 URL에 맞는 특수한 클래스를 생성해서 받음
     *
     * 장점
     * Member라는 자주 사용되는 엔티티가 API 스펙에 영향을 받지 않고 바뀔 수 있다.(컨트롤러와의 결합도를 줄일 수 있다.)
     * Member 엔티티를 바꾸지 않고 자유롭게 Validation 옵션을 설정해서 컨트롤러단에서 필터링 할 수 있다.
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest memberRequest){
        Member member = new Member();
        member.createMember(memberRequest.getName(), null);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @Data
    @AllArgsConstructor
    public static class CreateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    public static class CreateMemberResponse{
        private Long id;
    }
}