package kr.talanton.lala.product.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadProductForm {
	private Long pid;
	private String c1_code;
	private String c2_code;
	private String name;
	private int price;
	private int salePrice;
	private int maxPurchase;
	private String point;
	private int deposit;
	private String fee;
	private int delivery;
	private String[] feature;
	private String infoBtn;
	private String[] iname;
	private String[] idescription;
	private String optionBtn;
	private String[] oname;
	private String[] odescription;
	private String[] oprice;
	private String userid;
	private int readCount;
	private String introduction;
	private String pc_detail;
	private String mobile_detail;
	private String dguide;
	private String pc_delivery;
	private String mobile_delivery;
	private String exchange;
	private String pc_exchange;
	private String mobile_exchange;
	private String memo;
	private String expose;
	
	private List<MultipartFile> imgList;
}