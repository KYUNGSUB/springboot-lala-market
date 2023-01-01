package kr.talanton.lala.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.product.entity.ProductMapping;

public interface ProductMappingRepository extends JpaRepository<ProductMapping, Long> {

}