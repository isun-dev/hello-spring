package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // 필드에 Autowired를 주입하는 것은 추후에, 필드 변경이 어려워 질 수 있기 때문에 가급적 하지 않는게 좋다.
    private final MemberService memberService;


    // setter 주입 : 누군가가 MemberController를 클릭 했을 때 setter 주입이 열려 있어야 한다.
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    // MemberService 와 이 컨트롤러를 연결하는 방법은 생성자를 만드는 것이다
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
