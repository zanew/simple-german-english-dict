package com.zanewhitney.termlist;

import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.domain.Terms;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getTerms_from1to3_ReturnsTerms() {
        ResponseEntity<Terms> terms = this.testRestTemplate.getForEntity("/terms/{text}?n={resultsPerPage}", Terms.class, "geschehen", 3);

        assertThat(terms.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<Terms> body = Optional.ofNullable(terms.getBody());

        body.ifPresent(b -> {
            assertThat(b.getTerms().size() == 3);
            Term occurred = b.getTerms().get(0);

            assertThat(occurred.getTitle()).isEqualTo("occurred");
            assertThat(occurred.getLanguage()).isEqualTo(Locale.ENGLISH.getLanguage());
            assertThat(occurred.getGrammarFunction()).isEqualTo(Term.GrammarFunction.PAST_PARTICIPLE);
            assertThat(occurred.getGender()).isEqualTo(null);
            assertThat(occurred.getId().toString().length() > 0);
            assertThat(occurred.getDefinition()).isNotNull();
        });
    }

    @Test
    public void nonsense_term_HasNoDefinitions() {
        ResponseEntity<Terms> terms = this.testRestTemplate.getForEntity("/terms/{text}?n={resultsPerPage}", Terms.class, "uhtnesoahukcbaluetosauh", 100);

        assertThat(terms.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
