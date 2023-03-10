package kr.talanton.lala.product.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class ProductDTO {
	private Long pid;
	private String c1_code;
	private String c1_name; 
	private String c2_code;
	private String c2_name;
	private String name;
	private int price;
	private int salePrice;
	private int maxPurchase;
	private int deposit;
	private int delivery;
	private boolean newp;
	private boolean best;
	private boolean discount;
	private boolean info;
	private List<ProductInfoDTO> infoList;
	private boolean opt;
	private List<ProductOptionDTO> optionList;
	private String register_userid;
	private String register_name;
	private int readCount;
	private String introduction;
	private ProductMappingDTO pc_list;
	private List<ProductMappingDTO> pc_main;
	private ProductMappingDTO pc_expose;
	private ProductMappingDTO mobile_list;
	private List<ProductMappingDTO> mobile_main;
	private ProductMappingDTO mobile_expose;
	private String pc_detail;
	private String mobile_detail;
	private String pc_delivery;
	private String mobile_delivery;
	private String pc_exchange;
	private String mobile_exchange;
	private List<ProductHistoryDTO> historyList;
	private String memo;
	private String expose;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	public int getDepositFee() {
		return price * deposit / 100;
	}
}