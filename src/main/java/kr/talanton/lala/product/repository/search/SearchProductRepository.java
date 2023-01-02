package kr.talanton.lala.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.talanton.lala.product.dto.ProductPageRequestDTO;

public interface SearchProductRepository {
	Page<Object[]> searchPage(ProductPageRequestDTO dto, Pageable pageable);
}