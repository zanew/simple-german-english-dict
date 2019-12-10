package com.zanewhitney.termlist.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

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

