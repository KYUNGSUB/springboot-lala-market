package kr.talanton.lala.category.entity;

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
public class Category extends BaseEntity {
	@Id
	@Column(length=20, nullable=false)
	private String code;
	@Column(length=30, nullable=false)
	private String name;
	private boolean expose;
	private boolean gnb;
	@Column(nullable = false)
	private int step;
	@Column(nullable = false)
	private int seq;
	@Column(length=20)
	private String parent;
	
	public void changeExpose(boolean expose) {
		this.expose = expose;
	}
	
	public void changeGnb(boolean gnb) {
		this.gnb = gnb;
	}

	public void changeSeq(int seq) {
		this.seq = seq;
	}
}