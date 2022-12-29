package kr.talanton.lala.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.product.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

}