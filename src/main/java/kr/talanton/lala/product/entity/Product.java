package kr.talanton.lala.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import kr.talanton.lala.category.entity.Category;
import kr.talanton.lala.common.entity.BaseEntity;
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
@ToString(exclude={"register", "category1", "category2"})
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category2;
	
	@Column(length=60, nullable=false)
	private String name;
	
	@Column(nullable=false)
	private int price;
	
	@Column(nullable=false)
	private int saleprice;
	
	@Column(name="maxpurchase", columnDefinition="int default 10")
	private int maxpurchase;
	
	@Column(name="deposit", columnDefinition="int default 0")
	private int deposit;
	
	@Column(name="delivery", columnDefinition="int default 0")
	private int delivery;
	
	private boolean newp;
	private boolean best;
	private boolean discount;
	private boolean info;
	private boolean opt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member register;
	
	@Column(name="readcount", columnDefinition="int default 0")
	private int readcount;
	
	@Column(length=100, nullable=false)
	private String introduction;
	
	@Column(columnDefinition = "TEXT", nullable=false)
	private String pc_detail;
	
	@Column(columnDefinition = "TEXT", nullable=false)
	private String mobile_detail;
	
	@Column(columnDefinition = "TEXT")
	private String pc_delivery;
	
	@Column(columnDefinition = "TEXT")
	private String mobile_delivery;

	@Column(columnDefinition = "TEXT")
	private String pc_exchange;
	
	@Column(columnDefinition = "TEXT")
	private String mobile_exchange;
	
	@Column(length=300)
	private String memo;
	
	@Column(length=10, nullable=false)
	private String expose;

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}

	public void setNewp(boolean newp) {
		this.newp = newp;
	}

	public void setBest(boolean best) {
		this.best = best;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public void setInfo(boolean info) {
		this.info = info;
	}

	public void setOpt(boolean opt) {
		this.opt = opt;
	}

	public void setPc_delivery(String pc_delivery) {
		this.pc_delivery = pc_delivery;
	}

	public void setMobile_delivery(String mobile_delivery) {
		this.mobile_delivery = mobile_delivery;
	}

	public void setPc_exchange(String pc_exchange) {
		this.pc_exchange = pc_exchange;
	}

	public void setMobile_exchange(String mobile_exchange) {
		this.mobile_exchange = mobile_exchange;
	}
}