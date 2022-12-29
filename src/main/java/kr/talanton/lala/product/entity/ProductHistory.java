package kr.talanton.lala.product.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.talanton.lala.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude={"main", "register"})
@EntityListeners(value={AuditingEntityListener.class})	// Entity 객체가 생성/변경되는 것을 감지
public class ProductHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hid;
	
	@Column(nullable=false)
	private int item;
	
	@CreatedDate		// JPA에서 엔티티의 생성시간을 자동으로 처리
	@Column(name="timeAt", updatable=false)
	private LocalDateTime timeAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product main;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member register;
}