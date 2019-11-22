package com.zanewhitney.termlist.domain;

import com.zanewhitney.termlist.TestUtilities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    @Test
    public void findTermsByName_ReturnsTerms() {
        // given
        List<Term> terms = TestUtilities.getThreeDefinitions2();
        Term term = terms.get(0);
        testEntityManager.persist(term);
        testEntityManager.flush();

        // when
        List<Term> foundTerms = this.repository.findTermsBySearchQueryLike("occurred");
        assertThat(foundTerms.size()).isGreaterThan(0);

        Term foundTerm = foundTerms.get(0);

        // then
        assertThat(foundTerm.getTitle()).isEqualTo(term.getTitle());
        assertThat(foundTerm.getLanguage()).isEqualTo(term.getLanguage());
        assertThat(foundTerm.getGrammarFunction()).isEqualTo(term.getGrammarFunction());
        assertThat(foundTerm.getGender()).isEqualTo(term.getGender());
        assertThat(foundTerm.getId()).isEqualTo(term.getId());
        assertThat(foundTerm.getDefinition()).isEqualTo(term.getDefinition());
    }

    @Test
    public void termWithDefinitionId_ReturnsValidTerm() {
        List<Term> terms = TestUtilities.getThreeDefinitions2();
        Term term = terms.get(0);
        testEntityManager.persist(term);
        testEntityManager.flush();

        List<Term> foundTerms = this.repository.findTermsBySearchQueryLike("occurred");
        assertThat(foundTerms.size()).isGreaterThan(0);

        Term foundTerm = foundTerms.get(0);

        Term definitionTerm = this.repository.getTermById(foundTerm.getDefinition());

        assertThat(foundTerm.getTitle()).isEqualTo("geschehen");
        assertThat(foundTerm.getLanguage()).isEqualTo(Locale.GERMAN.getLanguage());
        assertThat(foundTerm.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(foundTerm.getGender()).isNull();
        assertThat(foundTerm.getId()).isNotNull();
        assertThat(foundTerm.getDefinition()).isEqualTo(foundTerm.getDefinition());
    }

    @Test
    public void nonsenseSearchTermReturnsNothing() {
        // given
        List<Term> terms = TestUtilities.getThreeDefinitions2();
        Term term = terms.get(0);
        testEntityManager.persist(term);
        testEntityManager.flush();

        // when
        List<Term> foundTerms = this.repository.findTermsBySearchQueryLike("hucreasdkrsdakrsdeoarcsk");
        assertThat(foundTerms.size()).isEqualTo(0);
    }
}
