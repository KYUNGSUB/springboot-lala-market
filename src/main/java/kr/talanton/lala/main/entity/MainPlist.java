package kr.talanton.lala.main.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import kr.talanton.lala.common.entity.BaseEntity;
import kr.talanton.lala.product.entity.Product;
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
public class MainPlist extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MainPmain main;
		
	private int position;

	@OneToOne(fetch = FetchType.LAZY)
	private Product product;
}