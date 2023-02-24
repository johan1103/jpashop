package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {

    List<Member> findByName(String userName);
    @Query("select m from Member m where m.name =: username")
    Page<Member> findByUsername(@Param(value = "username")String username);
}
