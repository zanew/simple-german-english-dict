package com.zanewhitney.termlist.service;

import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.TermsRepository;

import java.util.List;
import java.util.UUID;

public class TermsService {

    TermsService(TermsRepository termsRepository) {
    }

    public List<Term> getTerms(String searchTerm, int resultsPerPage) {
        return null;
    }

    public List<Term> getTerms(String searchTerm) {
        return null;
    }

    Term getTermById(UUID id) { return null; }
}
