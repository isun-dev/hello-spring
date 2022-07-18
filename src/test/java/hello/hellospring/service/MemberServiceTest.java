package hello.hellospring.service;


// 테스트코드를 작성할 때는 함수명을 한글로 작성해도 된다.

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void 회원가입() {
        // given -> 무언가가 주어졌을 때
        Member member = new Member();
        member.setName("john");
        // when -> 무언가를 실행했을 때
        Long saveId = memberService.join(member);

        // then -> 어떠한 결과가 나와야 한다.
        Member findMember = memberService.findOne(saveId).get();
        // assertThat(member.getName())
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}