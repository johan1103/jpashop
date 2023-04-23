package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {

    List<Member> findByName(String userName);
    @Query(value = "select m from Member m",countQuery = "select count(m.name) from Member m")
    Page<Member> findAllMember(Pageable pageable);
    @Query(value = "select m from Member m join fetch m.orders")
    Slice<Member> findAllMemberBySlice(Pageable pageable);
}
