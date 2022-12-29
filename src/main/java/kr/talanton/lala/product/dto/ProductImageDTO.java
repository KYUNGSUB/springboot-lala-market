package kr.talanton.lala.product.dto;

import java.io.File;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class ProductImageDTO {
	private String fileName;	// 원래 파일이름
	private String uploadPath;	// 저장 경로 (yyyy/MM/dd)
	private String uuid;		// UUID 값
	private String fileType;	// 이미지 여부('1': 이미지, '2': pc_list, '3': pc_main, '4': pc_expose
								//          '0': 일반, '5':mobile_list, '6': mobile_main, '7': mobile_expose
	/* - 게시판의 게시글에 대한 이미지
	 *   + kind='board', bno=bno(게시글 번호)
	 * - 상품정보에 포함되는 이미지
	 *   + kind='product', bno=pid(상품번호)
	 */
	private Long bno;			// 아이디(게시글, 제품 등)
	private String kind;		// 
	
	public String getFileCallPath() {
		return uploadPath + File.separator + uuid + "_" + fileName;
	}
	
	public String getThumbnailFileCallPath() {
		return uploadPath + File.separator + "s_" + uuid + "_" + fileName;
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			.queryParam("fileName", getFileCallPath());
		return builder.toUriString();
	}
}