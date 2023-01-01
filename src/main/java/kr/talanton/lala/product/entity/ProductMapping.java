package kr.talanton.lala.product.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import kr.talanton.lala.attach.entity.AttachFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "product") //연관 관계시 항상 주의
public class ProductMapping {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AttachFile attach;
	
    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Product product;
    
    private String kind;	// '1': pc_list, '2': pc_main, '3': pc_expose, '4':mobile_list, '5': mobile_main, '6': mobile_expose
}