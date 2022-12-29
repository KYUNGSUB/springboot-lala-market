package kr.talanton.lala.product.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductHistoryDTO {
	public static final int REGISTER = 1;
	public static final int MODIFY = 2;
	public static final int REMOVE = 3;
	
	private Long hid;
	private int item;	// 1:상품등록, 2:상품수정, 3:상품삭제
	private LocalDateTime timeAt;
	private String register_userid;
	private Long main_pid;
}