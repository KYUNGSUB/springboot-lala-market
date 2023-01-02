package kr.talanton.lala.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.repository.search.SearchProductRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, SearchProductRepository {
	// 게시글 목록 화면
	@Query(value ="SELECT p, c1, c2, r " +
			" FROM Product p " +
			" LEFT JOIN p.register r " +
			" LEFT JOIN p.category1 c1 " +
			" LEFT JOIN p.category2 c2 " +
			" GROUP BY p",
			countQuery ="SELECT count(p) FROM Product p")
	Page<Object[]> getProductWithRegister(Pageable pageable);
}