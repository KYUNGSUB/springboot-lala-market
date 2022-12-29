package kr.talanton.lala.banner.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {
	private Long bid;
	private int kind;		// 배너 종류(kind) : 1(GNB), 2(메인), 3(스타일숍 리스트), 4(오픈숍 리스트),
							//		5(서브 메뉴), 6(커뮤니티 리스트), 7(고객센터), 8(상품 주문 완료)
	private int position;	// 배너 위치(position) : 1(상단), 2(오른쪽), 3(왼쪽), 4(하단)
	private int location;	// 노출방식(location) : 1(슬라이더), 2(랜덤), 3(로그인 전/후), 4(노출하지 않음)
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	@Builder.Default
	private List<BannerMappingDTO> mappingList = new ArrayList<>();
	
	public String getBannerLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("kind", kind)
				.queryParam("position", position);
		return builder.toUriString();
	}

	public List<BannerMappingDTO> getMappingList() {
		return mappingList;
	}
	
	public void addBannerMappingToList(BannerMappingDTO dto) {
		mappingList.add(dto);
	}
}