package kr.talanton.lala.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.talanton.lala.main.entity.MainPmain;

public interface MainPmainRepository extends JpaRepository<MainPmain, String> {
	@Query("select ma, li, p from MainPmain ma " + 
			"left outer join MainPlist li on li.main = ma " +
			"left outer join Product p on li.product = p " + 
			"where ma.kind = :kind")
	Object[] getMainProductManagementInfo(@Param("kind") String kind);
}