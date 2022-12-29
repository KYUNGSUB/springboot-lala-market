package kr.talanton.lala.attach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.attach.entity.AttachFile;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {

}