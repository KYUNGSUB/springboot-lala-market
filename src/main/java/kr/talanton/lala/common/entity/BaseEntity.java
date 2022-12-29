package kr.talanton.lala.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass		// 테이블로 생성되지 않음
@EntityListeners(value={AuditingEntityListener.class})	// Entity 객체가 생성/변경되는 것을 감지
@Getter
public abstract class BaseEntity {
	@CreatedDate		// JPA에서 엔티티의 생성시간을 자동으로 처리
	@Column(name="regdate", updatable=false)
	private LocalDateTime regDate;
	
	@LastModifiedDate	// JPA에서 엔티티의 수정시간을 자동으로 처리
	@Column(name="moddate")
	private LocalDateTime modDate;
}
