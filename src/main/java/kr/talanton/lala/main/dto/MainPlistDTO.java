package kr.talanton.lala.main.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPlistDTO {
	private Long lid;
	private String kind;
	private int position;
	private Long itemId;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}