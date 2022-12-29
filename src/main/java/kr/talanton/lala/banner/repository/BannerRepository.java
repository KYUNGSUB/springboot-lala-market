package kr.talanton.lala.banner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.talanton.lala.banner.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
	@Query("select b, bm, af from Banner b " +
			"left outer join BannerMapping bm on bm.banner = b " +
			"left outer join AttachFile af on bm.attach = af " +
			"where b.kind = :kind and b.position = :position")
	List<Object[]> getBanner(@Param("kind") int kind, @Param("position") int position);
}