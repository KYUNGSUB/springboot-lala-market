package kr.talanton.lala.product.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class ProductImage {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    private String fileType;	// 이미지 여부('1': 이미지, '2': pc_list, '3': pc_main, '4': pc_expose
								//          '0': 일반, '5':mobile_list, '6': mobile_main, '7': mobile_expose
    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Product product;
}