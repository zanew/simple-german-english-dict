package com.zanewhitney.termlist.web;

import com.zanewhitney.termlist.TestUtilities;
import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.service.TermsService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(TermRestController.class)
public class TermsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TermsService termsService;

    @Before
    public void setUp() {

    }

    @Test
    public void getTerms_NotFound_Returns404() throws Exception {
        when(this.termsService.getTerms(any())).thenReturn(null);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3)).andExpect(status().isNotFound());
    }

    @Test
    public void getTerms_ReturnsTerms() throws Exception {
        List<Term> terms = TestUtilities.getThreeDefinitions2();

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data[1]").exists())
                .andExpect(jsonPath("$.data[2]").exists());
    }

    @Test
    public void en_term_HasRelevantProperties() throws Exception {
        List<Term> terms = TestUtilities.getThreeDefinitions2();

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(jsonPath("$.data[0].attributes.title").value("occurred"))
                .andExpect(jsonPath("$.data[0].attributes.lang").value("en"))
                .andExpect(jsonPath("$.data[0].attributes.grammar_type").value("verb"))
                .andExpect(jsonPath("$.data[0].attributes.gender").doesNotExist())
                .andExpect(jsonPath("$.data[0].attributes.id").value(greaterThan(0)));
    }

    @Test
    public void de_term_HasRelevantProperties() throws Exception {
        List<Term> terms = TestUtilities.getThreeDefinitions2();

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(jsonPath("$.data[0].relationships.title").value("geschehen"))
                .andExpect(jsonPath("$.data[0].relationships.lang").value("de"))
                .andExpect(jsonPath("$.data[0].relationships.grammar_type").value("verb"))
                .andExpect(jsonPath("$.data[0].relationships.gender").value("f"))
                .andExpect(jsonPath("$.data[0].relationships.id").value(greaterThan(0)));
    }
}
