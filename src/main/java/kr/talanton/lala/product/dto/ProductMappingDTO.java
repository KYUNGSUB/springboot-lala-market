package kr.talanton.lala.product.dto;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.entity.AttachFile;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.ProductMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductMappingDTO {
	private Long mid;
	private AttachFileDTO afDTO;
	private Long mainPid;
	private String kind;
	
	public ProductMapping dtoToEntity() {
		AttachFile af = AttachFile.builder().inum(afDTO.getInum()).build();
		Product product = Product.builder().pid(mainPid).build();
		ProductMapping entity = ProductMapping.builder()
				.mid(mid)
				.attach(af)
				.product(product)
				.kind(kind)
				.build();
		return entity;
	}
}