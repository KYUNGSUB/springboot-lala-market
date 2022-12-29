package kr.talanton.lala.product.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductInfoDTO {
	private int pi_id;
	private String name;
	private String description;
	private Long main_pid;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}