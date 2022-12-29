package kr.talanton.lala.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.product.entity.ProductHistory;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {

}