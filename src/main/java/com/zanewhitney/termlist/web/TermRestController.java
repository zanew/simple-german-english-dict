package com.zanewhitney.termlist.web;

import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.Terms;
import com.zanewhitney.termlist.service.TermsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TermRestController {
    private final TermsService termsService;

    TermRestController(TermsService termsService) {
        this.termsService = termsService;
    }

    @GetMapping("/terms/{text}")
    public Terms getTerms(@PathVariable String text, @RequestParam Optional<Integer> n) {
        List<Term> terms;
        if (n.isPresent()) {
            terms = this.termsService.getTerms(text, n.orElse(0));
        } else {
            terms = this.termsService.getTerms(text);
        }

        if (terms.size() == 0) {
            throw new TermNotFoundException();
        } else {
            return new Terms(terms);
        }
    }
}
