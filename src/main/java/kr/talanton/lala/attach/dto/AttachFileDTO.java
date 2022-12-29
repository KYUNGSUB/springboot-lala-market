package kr.talanton.lala.attach.dto;

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
public class AttachFileDTO {
	private Long inum;
	private String uuid;
	private String imgName;
	private String path;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}