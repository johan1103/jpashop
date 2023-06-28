package jpabook.jpashop.api.lecture;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@RequestBody @Valid UpdateMemberRequest memberRequest,
                                               @PathVariable(value = "id") Long id){
        Long updateId = memberService.update(id, memberRequest.getName());
        return new UpdateMemberResponse(updateId,memberRequest.getName());
    }

    @Data
    @AllArgsConstructor
    public static class UpdateMemberResponse{
        private Long id;
        private String name;
    }
    @Data
    @AllArgsConstructor
    public static class UpdateMemberRequest{
        private String name;
        public UpdateMemberRequest(){

        }
    }

    /**
     * 주의
     * 이유는 모르겠지만, MVC에서 RequestBody로 객체 파라미터를 받으려면
     * 해당 객체는 무조건 빈 생성자도 가지고 있어야 한다.
     */
    @Data
    @AllArgsConstructor
    public static class CreateMemberRequest{
        @NotEmpty
        private String name;
        public CreateMemberRequest(){

        }
    }

    @Data
    @AllArgsConstructor
    public static class CreateMemberResponse{
        private Long id;
    }


    /**
     * 전체 멤버 조회 API
     */

    /**
     * V1 멤버 조회 API
     * 단점
     * 1. List 형태로의 반환
     *      json으로 변환시에 컬랙션 형태의 경우 배열로 반환되는데 이는 API 스펙의 확장성을 크게 해지는 방식이다.
     *      ex. 멤버 리스트와 그 중 지역이 서울인 멤버의 수도 같이 보내주세요
     * 2. 엔티티 자체를 반환
     *      엔티티를 반환하게 되면 위의 문제점과 같이 API스펙이 엔티티에게 의존적이다
     *      필요 없는 정보까지 받게 된다. (제외 할 수는 있지만, 다른 모든 api 클라이언트에게도 제외된다.)
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    /**
     * V2 개선점
     * 1. 반환값을 컬랙션 형태가 아닌 단일 객체 인스턴스를 반환 했다.
     *      다른 추가 정보도 담을 수 있도록 확장성을 확보했다.
     * 2. 배열 내부의 인스턴스를 DTO로 변환했다.
     *      API 스펙이 DTO에게 의존적이며, 엔티티로부터 독립적으로 바뀌었다.
     */

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> membersV2(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtos = members.stream().map( m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<List<MemberDto>>(memberDtos);
    }

    @GetMapping("/api/v3/members")
    public Result<List<MemberDto>> membersV3(@RequestParam(value = "offset")Integer offset,
                                             @RequestParam(value = "size")Integer size){
        List<Member> members = memberService.findMembersJpa(offset,size).getContent();
        List<MemberDto> memberDtos = members.stream().map(m->new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<List<MemberDto>>(memberDtos);
    }
    @GetMapping("/api/v3-2/members")
    public Result<List<MemberDto>> membersV3_2(@RequestParam(value = "offset")Integer offset,
                                                                             @RequestParam(value = "size")Integer size){
        List<Member> members = memberService.findMembersSliceJpa(offset,size).getContent();
        List<MemberDto> memberDtos = members.stream().map(m->new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<List<MemberDto>>(memberDtos);
    }


    @Data
    @AllArgsConstructor
    public static class Result<T>{
        private T data;
    }
    @Data
    @AllArgsConstructor
    public static class MemberDto{
        private String name;
    }






}