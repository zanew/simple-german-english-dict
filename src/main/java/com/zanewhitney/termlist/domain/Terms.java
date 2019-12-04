package com.zanewhitney.termlist.domain;

import java.util.List;

public class Terms {
    private List<Term> terms;

    public List<Term> getTerms() {
        return terms;
    }

    public Terms(List<Term> terms) {
        this.terms = terms;
    }
}

