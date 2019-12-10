package com.zanewhitney.termlist.service;

import com.zanewhitney.termlist.TestUtilities;
import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.TermsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TermsServiceTests {
    @Mock
    private TermsRepository termsRepository;

    private TermsService termsService;

    @Before
    public void setUp() {
        termsService = new TermsService(termsRepository);
    }

    @Test
    public void getTerms_returnsTermsInfo() {
        given(termsRepository.findBySearchQueryLike("geschehen", PageRequest.of(0, 3)))
                .willReturn(TestUtilities.getDefinitions());

        List<Term> terms = termsService.getTerms("geschehen", 3);
        assertThat(terms.size() == 3);

        Term occurred = terms.get(0);

        assertThat(occurred.getTitle()).isEqualTo("occurred");
        assertThat(occurred.getLanguage()).isEqualTo(Locale.ENGLISH.getLanguage());
        assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(occurred.getGender()).isNull();
        assertThat(occurred.getId().length() > 0);
        assertThat(occurred.getDefinition()).isNotNull();
    }

    @Test
    public void getTerms_hasDefinition() {
        List<Term> threeDefs = TestUtilities.getDefinitions();
        Term def0 = threeDefs.get(0);
        Term def1 = threeDefs.get(1);
        given(termsRepository.getTermById(def0.getDefinition())).willReturn(def1);

        given(termsRepository.findBySearchQueryLike("geschehen", PageRequest.of(0, 3)))
                .willReturn(threeDefs);

        List<Term> terms = termsService.getTerms("geschehen", 3);
        Term occurred = terms.get(0);

        Term geschehen = termsService.getTermById(occurred.getDefinition());

        assertThat(geschehen.getTitle()).isEqualTo("geschehen");
        assertThat(geschehen.getLanguage()).isEqualTo(Locale.GERMAN.getLanguage());
        assertThat(geschehen.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(geschehen.getGender()).isNull();
        assertThat(occurred.getId().length() > 0);
        assertThat(geschehen.getDefinition()).isNotNull();
    }

    @Test
    public void getTerms_returnsTermWithDefinition() {
        given(termsRepository.findBySearchQueryLike("geschehen", PageRequest.of(0, 3)))
                .willReturn(TestUtilities.getDefinitions());

        List<Term> terms = termsService.getTerms("geschehen", 3);
        assertThat(terms.size() == 3);

        Term occurred = terms.get(0);
        String occurredUUID = occurred.getDefinition();

        assertThat(occurred.getTitle()).isEqualTo("occurred");
        assertThat(occurred.getLanguage()).isEqualTo(Locale.US.getLanguage());
        assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(occurred.getGender()).isNull();
        assertThat(occurred.getId().length() > 0);
        assertThat(occurred.getDefinition()).isNotNull();
    }

    @Test
    public void getNonsenseTerm_returnsNothing() {
        List<Term> terms = termsService.getTerms("sehtaosuehoh");
        assertThat(terms.size() == 0);
    }
}
