package kr.talanton.lala.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
@ToString(exclude="main")
public class ProductOption extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long po_id;
	
	@Column(nullable=false)
	private int gid;
	
	@Column(length=30, nullable=false)
	private String name;
	
	@Column(length=60, nullable=false)
	private String description;
	
	@Column(nullable=false)
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product main;
}