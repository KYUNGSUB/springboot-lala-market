package kr.talanton.lala.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.main.entity.MainPmain;

public interface MainPmainRepository extends JpaRepository<MainPmain, String> {
	MainPmain getMainProductManagementInfo(String kind);
}