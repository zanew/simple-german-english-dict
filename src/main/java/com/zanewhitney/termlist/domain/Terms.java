package com.zanewhitney.termlist.domain;

import java.util.List;

public class Terms {
    private List<Term> data;

    public List<Term> getData() {
        return data;
    }

    public Terms() {}

    public Terms(List<Term> terms) {
        this.data = terms;
    }
}

