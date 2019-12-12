package com.zanewhitney.termlist.web;

import com.zanewhitney.termlist.TestUtilities;
import com.zanewhitney.termlist.domain.Term;
import com.zanewhitney.termlist.service.TermsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.when;

@WebMvcTest(TermRestController.class)
public class TermsControllerTests {

    /*
    Mock Dictionary Expected Format:
    occurred -> geschehen
    happened -> geschehen
    events -> Geschehen
    geschehen -> occurred
    Geschehen -> events
    */

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TermsService termsService;

    @Test
    public void getTerms_NotFound_Returns404() throws Exception {
        when(this.termsService.getTerms(any())).thenReturn(null);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3)).andExpect(status().isNotFound());
    }

    @Test
    public void getTerms_ReturnsTerms() throws Exception {
        List<Term> terms = TestUtilities.getDefinitions(3);

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data[1]").exists())
                .andExpect(jsonPath("$.data[2]").exists());
    }

    @Test
    public void noProvidedResultCount_ReturnsFiveResults() throws Exception {
        List<Term> terms = TestUtilities.getDefinitions();

        when(this.termsService.getTerms("geschehen")).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}", "geschehen"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    public void en_term_HasRelevantProperties() throws Exception {
        List<Term> terms = TestUtilities.getDefinitions(3);

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(jsonPath("$.data[0].title").value("occurred"))
                .andExpect(jsonPath("$.data[0].language").value("en"))
                .andExpect(jsonPath("$.data[0].grammar_function").value("past_participle"))
                .andExpect(jsonPath("$.data[0].gender").doesNotExist())
                .andExpect(jsonPath("$.data[0].id").exists());
    }

    @Test
    public void de_term_HasRelevantProperties() throws Exception {
        List<Term> terms = TestUtilities.getDefinitions();

        when(this.termsService.getTerms("geschehen", 3)).thenReturn(terms);
        this.mockMvc.perform(get("/terms/{text}?n={resultsPerPage}", "geschehen", 3))
                .andExpect(jsonPath("$.data[3].title").value("geschehen"))
                .andExpect(jsonPath("$.data[3].language").value("de"))
                .andExpect(jsonPath("$.data[3].grammar_function").value("past_participle"))
                .andExpect(jsonPath("$.data[3].gender").doesNotExist())
                .andExpect(jsonPath("$.data[3].id").exists());
    }
}
