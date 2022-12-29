package kr.talanton.lala.terms.entity;

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
public class Terms extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tid;
	
	@Column(length=100, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable=false)
	private String content;
	
	private boolean expose;
	private boolean mandatory;
	
	public void changeTitle(String title) {
		this.title = title;
	}
	
	public void changeContent(String content) {
		this.content = content;
	}
	
	public void changeExpose(boolean expose) {
		this.expose = expose;
	}
	
	public void changeMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}