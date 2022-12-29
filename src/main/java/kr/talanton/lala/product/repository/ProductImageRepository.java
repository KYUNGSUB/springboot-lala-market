package kr.talanton.lala.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}