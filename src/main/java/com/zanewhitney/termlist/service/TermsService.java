package com.zanewhitney.termlist.service;

import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermsService {
    private final TermsRepository termsRepository;

    @Autowired
    TermsService(@Qualifier("termsRepository") TermsRepository termsRepository) { this.termsRepository = termsRepository; }

    public List<Term> getTerms(String searchTerm, int resultsPerPage) {
        if (resultsPerPage == 0) {
            return this.getTerms(searchTerm);
        } else {
            Pageable rpp = PageRequest.of(0, resultsPerPage);
            return this.termsRepository.findBySearchQueryLike(searchTerm, rpp);
        }
    }

    public List<Term> getTerms(String searchTerm) {
        return this.termsRepository.findTopTenTermsBySearchQueryLike(searchTerm);
    }

    Term getTermById(String id) {
        return this.termsRepository.getTermById(id);
    }
}
