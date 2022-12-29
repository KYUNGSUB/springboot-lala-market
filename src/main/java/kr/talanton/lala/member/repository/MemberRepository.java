package kr.talanton.lala.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.talanton.lala.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.fromSocial = :social and m.userid = :userid")
    Optional<Member> findByUserid(@Param("userid") String userid, @Param("social") boolean social);
}