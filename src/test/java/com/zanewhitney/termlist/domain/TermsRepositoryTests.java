package com.zanewhitney.termlist.domain;

import com.zanewhitney.termlist.TestUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TermsRepositoryTests {

    @Autowired
    private TermsRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void persistTerms() {
        List<Term> terms = TestUtilities.getDefinitions();

        for (Term term : terms) {
            testEntityManager.persist(term);
        }
        testEntityManager.flush();
    }

    @Test
    public void findTermsByName_ReturnsTerms() {
        // given
        List<Term> terms = TestUtilities.getDefinitions();
        Term term = terms.get(0);
        testEntityManager.persist(term);
        testEntityManager.flush();

        // when
        List<Term> foundTerms = this.repository.findBySearchQueryLike("occurred", PageRequest.of(0, 3));
        assertThat(foundTerms.size()).isGreaterThan(0);

        Term foundTerm = foundTerms.get(0);

        // then
        assertThat(foundTerm.getTitle()).isEqualTo(term.getTitle());
        assertThat(foundTerm.getLanguage()).isEqualTo(term.getLanguage());
        assertThat(foundTerm.getGrammarFunction()).isEqualTo(term.getGrammarFunction());
        assertThat(foundTerm.getGender()).isEqualTo(term.getGender());
        assertThat(foundTerm.getId()).isNotNull();
        assertThat(foundTerm.getDefinition()).isNotNull();
    }

    @Test
    public void termWithDefinitionId_ReturnsValidTerm() {
        List<Term> foundTerms = this.repository.findBySearchQueryLike("occurred", PageRequest.of(0, 3));
        assertThat(foundTerms.size()).isGreaterThan(0);

        Term foundTerm = foundTerms.get(0);

        Term definitionTerm = this.repository.getTermById(foundTerm.getDefinition());

        assertThat(definitionTerm.getTitle()).isEqualTo("geschehen");
        assertThat(definitionTerm.getLanguage()).isEqualTo(Locale.GERMAN.getLanguage());
        assertThat(definitionTerm.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(definitionTerm.getGender()).isNull();
        assertThat(definitionTerm.getId()).isNotNull();
        assertThat(definitionTerm.getDefinition()).isEqualTo(foundTerm.getId());
    }

    @Test
    public void nonsenseSearchTermReturnsNothing() {
        // when
        List<Term> foundTerms = this.repository.findBySearchQueryLike("hucreasdkrsdakrsdeoarcsk", PageRequest.of(0, 3));
        assertThat(foundTerms.size()).isEqualTo(0);
    }
}
