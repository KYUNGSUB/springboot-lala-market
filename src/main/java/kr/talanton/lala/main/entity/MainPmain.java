package kr.talanton.lala.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import kr.talanton.lala.common.entity.BaseEntity;
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
@ToString
public class MainPmain extends BaseEntity {
	@Id
	@Column(length=15, nullable=false)
	private String kind;
	
	private String title;
	private int display;	// 1[상품 등록순], 2[최근 7일간 조회수 높은순], 3[관리자 선택]
}