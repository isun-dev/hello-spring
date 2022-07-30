package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 스프링부트를 테스트할 때는 @SpringBootTest 로 한다
@SpringBootTest
@Transactional // 테스트가 끝나면 디비에 넣었던 데이터를 롤백해준다.
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService; // 테스트할 때는 편하게 사용하면 된다
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given -> 무언가가 주어졌을 때
        Member member = new Member();
        member.setName("john");
        // when -> 무언가를 실행했을 때
        Long saveId = memberService.join(member);

        // then -> 어떠한 결과가 나와야 한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("asd");

        Member member2 = new Member();
        member2.setName("asd");

        // when
        memberService.join(member1);
        // 간략
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

        // 정공법
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다324567");
//        }

        // then
    }


}