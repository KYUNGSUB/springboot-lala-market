package kr.talanton.lala.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.product.entity.ProductInfo;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

}