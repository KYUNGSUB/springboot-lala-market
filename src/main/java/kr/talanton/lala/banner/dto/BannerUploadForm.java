package kr.talanton.lala.banner.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BannerUploadForm {
	private Long bid;		// Banner id
	private int info_id;	// BannerMappingDTO id
	private int kind;
	private int position;
	private String location;
	private String url;
	private String alt;
	private String target;
	private String login;
	
	private MultipartFile bannerImg;
	
	public BannerDTO toBannerDTO() {
		BannerDTO dto = BannerDTO.builder()
				.bid(bid)
				.kind(kind)
				.position(position)
				.build();
		if(location.equals("slide")) {
			dto.setLocation(1);
		} else if(location.equals("random")) {
			dto.setLocation(2);
		} else if(location.equals("login")) {
			dto.setLocation(3);
		} else {
			dto.setLocation(4);
		}
		
		return dto;
	}

	public BannerMappingDTO toBannerMappingDTO() {
		BannerMappingDTO dto = BannerMappingDTO.builder()
				.bid(bid)
				.url(url)
				.alt(alt)
				.target(target)
				.build();
		if(login.equals("default")) {
			dto.setLoginBefore(0);
		} else if(login.equals("before")) {
			dto.setLoginBefore(1);
		} else {
			dto.setLoginBefore(2);
		}
		return dto;
	}
}