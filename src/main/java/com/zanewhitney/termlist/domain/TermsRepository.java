package com.zanewhitney.termlist.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository("termsRepository")
public interface TermsRepository extends JpaRepository<Term, String> {
    @Query("SELECT t FROM Term t WHERE t.title like ?1")
    List<Term> findTopTenTermsBySearchQueryLike(String searchQuery);

    @Query("SELECT t FROM Term t WHERE t.title like ?1")
    List<Term> findBySearchQueryLike(String searchQuery, Pageable pageable);

    @Query("SELECT t FROM Term t WHERE t.id = ?1")
    Term getTermById(String id);
}