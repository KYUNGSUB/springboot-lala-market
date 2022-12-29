package kr.talanton.lala.banner.dto;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannerMappingDTO {
	private Long mid;
	private AttachFileDTO attach;
	private Long bid;
	private String url;
	private String alt;
	private String target;
	private int loginBefore;	// default(0), 로그인 전(before:1), 로그인 후(after:2)
}