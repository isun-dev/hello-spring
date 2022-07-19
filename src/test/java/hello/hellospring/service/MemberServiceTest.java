package hello.hellospring.service;


// 테스트코드를 작성할 때는 함수명을 한글로 작성해도 된다.
// 테스트코드를 실행시켰을 때 성공적으로 돌아가는 것도 중요하지만, 예외가 잘 발생하는지 확인하는 것도
// 중요하다.

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}