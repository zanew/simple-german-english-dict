package com.zanewhitney.termlist.service;

import com.zanewhitney.termlist.TestUtilities;
import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.TermsRepository;
import com.zanewhitney.termlist.web.TermNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
        given(termsRepository.findTermsBySearchQueryLike("geschehen"))
                .willReturn(TestUtilities.getThreeDefinitions2());

        List<Term> terms = termsService.getTerms("geschehen", 3);
        assertThat(terms.size() == 3);

        Term occurred = terms.get(0);

        assertThat(occurred.getTitle()).isEqualTo("occurred");
        assertThat(occurred.getLanguage()).isEqualTo(Locale.ENGLISH.getLanguage());
        assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(occurred.getGender()).isNull();
        assertThat(occurred.getId().toString().length() > 0);
        assertThat(occurred.getDefinition()).isNotNull();
    }

    @Test
    public void getTerms_hasDefinition() {
        given(termsRepository.findTermsBySearchQueryLike("geschehen"))
                .willReturn(TestUtilities.getThreeDefinitions2());

        List<Term> terms = termsService.getTerms("geschehen", 3);
        Term occurred = terms.get(0);

        Term geschehen = termsService.getTermById(occurred.getDefinition());

        assertThat(geschehen.getTitle()).isEqualTo("geschehen");
        assertThat(occurred.getLanguage()).isEqualTo(Locale.GERMAN.getLanguage());
        assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(occurred.getGender()).isNotNull();
        assertThat(occurred.getId().toString().length() > 0);
        assertThat(occurred.getDefinition()).isNotNull();
    }

    @Test
    public void getTerms_returnsTermWithDefinition() {
        given(termsRepository.findTermsBySearchQueryLike("geschehen")).willReturn(TestUtilities.getThreeDefinitions2());

        List<Term> terms = termsService.getTerms("geschehen", 3);
        assertThat(terms.size() == 3);

        Term occurred = terms.get(0);
        UUID occurredUUID = occurred.getDefinition();

        assertThat(occurred.getTitle()).isEqualTo("geschehen");
        assertThat(occurred.getLanguage()).isEqualTo(Locale.GERMAN.getLanguage());
        assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
        assertThat(occurred.getGender()).isNull();
        assertThat(occurred.getId().toString().length() > 0);
        assertThat(occurred.getDefinition()).isNull();
    }

    @Test
    public void getNonsenseTerm_returnsNothing() {
        given(termsRepository.findTermsBySearchQueryLike("geschehen")).willReturn(TestUtilities.getThreeDefinitions2());

        List<Term> terms = termsService.getTerms("sehtaosuehoh");
        assertThat(terms.size() == 0);
    }

    @Test(expected = TermNotFoundException.class)
    public void getTerms_whenTermNotFound() {
        given(termsRepository.findTermsBySearchQueryLike("geschehen")).willReturn(null);

        List<Term> terms = termsService.getTerms("geschehen", 3);

        assertThat(terms.get(0)).isNotNull();
    }
}

