package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    // MemberService 와 이 컨트롤러를 연결하는 방법은 생성자를 만드는 것이다
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
