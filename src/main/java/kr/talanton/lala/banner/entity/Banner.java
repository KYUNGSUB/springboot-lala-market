package kr.talanton.lala.banner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

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
@Table(indexes= {@Index(name="idx_banner", columnList="kind, position")})
public class Banner extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bid;
	
	@Column(nullable = false)
	private int kind;
	
	@Column(nullable = false)
	private int position;
	
	@Column(nullable = false)
	private int location;

	public void setLocation(int location) {
		this.location = location;
	}
}