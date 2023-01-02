package kr.talanton.lala.product.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import kr.talanton.lala.attach.entity.QAttachFile;
import kr.talanton.lala.category.entity.QCategory;
import kr.talanton.lala.member.entity.QMember;
import kr.talanton.lala.product.dto.ProductPageRequestDTO;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.QProduct;
import kr.talanton.lala.product.entity.QProductMapping;
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
		QProductMapping mapping = QProductMapping.productMapping;
		QAttachFile attach = QAttachFile.attachFile;
		QCategory category = QCategory.category;
		QMember register = QMember.member;
		JPQLQuery<Product> jpqlQuery = from(product);
		jpqlQuery.leftJoin(mapping).on(mapping.product.eq(product));
		jpqlQuery.leftJoin(attach).on(mapping.attach.eq(attach));
		jpqlQuery.leftJoin(product.category1);
		jpqlQuery.leftJoin(product.category2);
		jpqlQuery.leftJoin(product.register);
		JPQLQuery<Tuple> tuple = jpqlQuery.select(product, mapping, attach, product.category1, product.category2, product.register);
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
		if(dto.getDetail() != null && dto.getDetail().equals("yes")) {
			if(dto.getCategory1() != null) {
				BooleanExpression categoryExpression = product.category1.code.eq(dto.getCategory1());
				booleanBuilder.and(categoryExpression);
			}
			if(dto.getCategory2() != null) {
				BooleanExpression categoryExpression = product.category2.code.eq(dto.getCategory2());
				booleanBuilder.and(categoryExpression);
			}
			if(dto.getPriceFrom() > 0 && dto.getPriceTo() > 0) {
				BooleanExpression priceExpression = product.price.between(dto.getPriceFrom(), dto.getPriceTo());
				booleanBuilder.and(priceExpression);
			}
			if(dto.getRegFrom() != null && dto.getRegTo() != null) {
				BooleanExpression regExpression = product.regDate.between(dto.getRegFrom(), dto.getRegTo());
				booleanBuilder.and(regExpression);
			}
			if(dto.getExposeArr() != null) {
				String[] typeArr = dto.getExposeArr();
	            //검색 조건을 작성하기
	            BooleanBuilder conditionBuilder = new BooleanBuilder();
	            for (String t:typeArr) {
	                switch (t){
	                case "진열":
	                    conditionBuilder.or(product.expose.contains(t));
	                    break;
	                case "품절":
	                    conditionBuilder.or(product.expose.contains(t));
	                    break;
	                case "숨김":
	                    conditionBuilder.or(product.expose.contains(t));
	                    break;
	                }
	            }
	            booleanBuilder.and(conditionBuilder);
			}
			if(dto.getKind() == 1) {
				BooleanExpression kindExpression = product.newp.eq(true);
				booleanBuilder.and(kindExpression);
			} else if(dto.getKind() == 2) {
				BooleanExpression kindExpression = product.best.eq(true);
				booleanBuilder.and(kindExpression);
			} else if(dto.getKind() == 3) {
				BooleanExpression kindExpression = product.discount.eq(true);
				booleanBuilder.and(kindExpression);
			}
		}
		tuple.where(booleanBuilder);
		//order by
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
	    tuple.groupBy(product);
	    //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
	    List<Tuple> result = tuple.fetch();
	    log.info(result);
	    long count = tuple.fetchCount();
        log.info("COUNT: " +count);
        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
	}
}