package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // jpa는 EntityManager로 모든것이 동작한다.
    // build.gradle에 data-jpa를 추가하면 스프링부트가 자동으로 EntityManager를 생성해준다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // persist: 영구저장하다라는 것을 의미
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // Member.class는 조회할 타입을 의미한다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // jpql
        // 객체를 대상으로 쿼리를 날리는 것. 그러면 sql로 번역이 된다.

        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

        return result;
    }
}
