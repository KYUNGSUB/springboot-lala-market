package kr.talanton.lala.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.talanton.lala.policy.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
	@Transactional
	@Modifying
	@Query("delete from Policy p where p.code = :code")
	int deleteByCode(@Param("code") int code);
}