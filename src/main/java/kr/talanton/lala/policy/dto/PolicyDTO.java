package kr.talanton.lala.policy.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PolicyDTO {
	private Integer pid;
	private Integer code;
	private String category;
	private String name;
	private String value;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}