package kr.talanton.lala.product.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductOptionDTO {
	private Long po_id;
	private int gid;
	private String name;
	private String description;
	private int price;
	private Long main_pid;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}