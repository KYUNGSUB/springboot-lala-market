package kr.talanton.lala.terms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.talanton.lala.terms.entity.Terms;

public interface TermsRepository extends JpaRepository<Terms, Long> {

}