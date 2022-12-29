package kr.talanton.lala.terms.dto;

import java.time.LocalDateTime;

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
public class TermsDTO {
	private Long tid;
	private String title;
	private String content;
	private boolean expose;
	private boolean mandatory;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}