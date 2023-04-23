package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.log.CustomTracer;
import jpabook.jpashop.log.TraceStatus;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CustomTracer customTracer;

    @GetMapping(value = "members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(),memberForm.getStreet(),memberForm.getZipcode());
        Member member = new Member();
        member.createMember(memberForm.getName(), address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model){

        List<Member> members = null;
        TraceStatus status = null;
        try {
            status = customTracer.begin(0,"list");
            members = memberService.findMembers(status);
            customTracer.complete(status,"list",null);
        }catch (Exception e){
            customTracer.complete(status,"list",e);
            throw e;
        }
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
