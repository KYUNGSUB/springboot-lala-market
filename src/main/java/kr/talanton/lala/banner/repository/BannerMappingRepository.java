package kr.talanton.lala.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.banner.entity.BannerMapping;

public interface BannerMappingRepository extends JpaRepository<BannerMapping, Long> {

}