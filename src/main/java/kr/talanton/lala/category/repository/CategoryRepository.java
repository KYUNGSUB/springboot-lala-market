package kr.talanton.lala.category.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.talanton.lala.category.dto.CategoryDTO;
import kr.talanton.lala.category.dto.MaxCount;
import kr.talanton.lala.category.dto.OptionInfo;
import kr.talanton.lala.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	@Query(value="select c from Category c where c.step = 1 order by c.seq asc")
	List<Category> getListFirst();

	@Query("select c from Category c where c.step = 2 and c.parent = :parent order by c.seq asc")
	List<Category> getListSecond(@Param("parent") String code);

	@Query("select count(c) from Category c where c.step = :step")
	int getSeq1(@Param("step") int step);

	@Query("select count(c) from Category c where c.step = :step and c.parent = :parent")
	int getSeq2(@Param("step") int step, @Param("parent") String parent);

	@Query("select c from Category c where c.step=1 and c.seq = :seq")
	Category getFirstCategoryBySeq(@Param("seq") int seq);

	@Query("select c from Category c where c.step = 2 and c.parent = :parent and c.seq = :seq")
	Category getSecondCategoryBySeq(@Param("parent") String parent, @Param("seq") int seq);

	@Transactional
	@Modifying
	@Query("update Category c set c.seq=c.seq-1 where c.step=1 and c.seq > :seq")
	int updateSequence1(@Param("seq") int seq);

	@Transactional
	@Modifying
	@Query("update Category c set c.seq=c.seq-1 where c.parent = :parent and c.seq > :seq")
	int updateSequence2(@Param("parent") String parent, @Param("seq") int seq);

	@Transactional
	@Modifying
	@Query("delete from Category c where c.parent = :parent")
	int deleteSecondCategory(@Param("parent") String parent);

	@Query("select c.expose, c.gnb from Category c where c.code = :code")
	Object[] getOptionInfo(@Param("code") String code);

	@Query(value="select max(substr(code,4,3)), count(code) from category where parent = :parent", nativeQuery=true)
	Object[] getMaxCount(@Param("parent") String parent);

	@Query(value="select substr(code,4,3) from category where parent = :parent", nativeQuery=true)
	List<Object[]> getCodes(@Param("parent") String parent);
}