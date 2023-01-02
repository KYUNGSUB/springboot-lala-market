package kr.talanton.lala.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.talanton.lala.category.entity.QCategory;
import kr.talanton.lala.member.entity.QMember;
import kr.talanton.lala.product.dto.ProductPageRequestDTO;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.QProduct;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchProductRepositoryImpl extends QuerydslRepositorySupport implements SearchProductRepository {
	public SearchProductRepositoryImpl() {
		super(Product.class);
	}

	@Override
	public Page<Object[]> searchPage(ProductPageRequestDTO dto, Pageable pageable) {
		log.info("searchOage... " + dto);
		QProduct product = QProduct.product;
		QCategory category1 = QCategory.category;
		QCategory category2 = QCategory.category;
		QMember register = QMember.member;
		JPQLQuery<Product> jpqlQuery = from(product);
		jpqlQuery.leftJoin(category1).on(product.category1.eq(category1));
		jpqlQuery.leftJoin(category2).on(product.category2.eq(category2));
		jpqlQuery.leftJoin(register).on(product.register.eq(register));
		JPQLQuery<Tuple> tuple = jpqlQuery.select(product, category1, category2, register);
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = product.pid.gt(0L);
		booleanBuilder.and(expression);
		// 주 검색
		if(dto.getType() != null && !dto.getType().equals("none")) {
			BooleanExpression keywordExp;
			if(dto.getType().equals("name")) {
				keywordExp = product.name.contains(dto.getKeyword());
			} else {
				keywordExp = register.userid.contains(dto.getKeyword());
			}
			booleanBuilder.and(keywordExp);
		}
		// 세부 검색
		if(dto.getDetail().equals("yes")) {
			if(dto.getCategory1() != null) {
				BooleanExpression categoryExpression = category1.code.eq(dto.getCategory1());
				booleanBuilder.and(categoryExpression);
			}
			if(dto.getCategory2() != null) {
				BooleanExpression categoryExpression = category2.code.eq(dto.getCategory2());
				booleanBuilder.and(categoryExpression);
			}
			if(dto.getPriceFrom() > 0 && dto.getPriceTo() > 0) {
				BooleanExpression priceFromExpression = product.price.between(dto.getPriceFrom(), dto.getPriceTo());
				booleanBuilder.and(priceFromExpression);
			}
		}
		return null;
	}

}
