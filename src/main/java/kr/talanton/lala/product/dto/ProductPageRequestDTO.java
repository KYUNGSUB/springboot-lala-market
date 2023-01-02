package kr.talanton.lala.product.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductPageRequestDTO {
	private int page;
    private int size;
    private String type;
    private String keyword;
    
    @Builder.Default
    private int kind = 0;		// 세부분류 : 0(모아보기,all), 1(신상품), 2(Best), 3(할인)
    @Builder.Default
    private int orderby = 0;	// 정렬조건 : 0(최신순), 1(낮은 가격순), 2(높은 가격순)
    
    private String category1;	// 1차 카테고리
	private String category2;	// 2차 카테고리
	private int priceFrom;		// 판매가격 하한가
	private int priceTo;		// 판매가격 상한가
	private String regFrom;		// 상품 등록일 하한일
	private String regTo;		// 상품 등록일 상한일
	private String[] exposeArr;	// 진열여부 : 진열(show), 품절(out), 숨김(hide)
	private String detail;		// 상세 검색 여부 : yes(상세 검색), no

    public ProductPageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }
    
    public ProductPageRequestDTO(int page, int size){
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page - 1, size, sort);
    }
}