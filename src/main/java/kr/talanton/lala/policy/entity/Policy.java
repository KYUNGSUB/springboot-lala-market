package kr.talanton.lala.policy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Policy extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	
	@Column(nullable=false)
	private int code;
	
	@Column(length=60, nullable=false)
	private String category;
	
	@Column(length=100, nullable=false)
	private String name;
	
	@Column(length=2000, nullable=false)
	private String value;

	public void setValue(String value) {
		this.value = value;
	}
}