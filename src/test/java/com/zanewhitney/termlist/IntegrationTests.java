package com.zanewhitney.termlist;

import com.zanewhitney.termlist.domain.Term;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TermlistApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void persistTerms() {
        List<Term> terms = TestUtilities.getDefinitions();

        for (Term term : terms) {
            Term t = testEntityManager.persistFlushFind(term);
        }
    }

    @Test
    public void getTerms_ReturnsTerms() throws Exception {
        this.mockMvc.perform(get("/terms/{text}", "geschehen"))
                .andExpect(jsonPath("$.data[0].title").value("geschehen"))
                .andExpect(jsonPath("$.data[0].language").value("de"))
                .andExpect(jsonPath("$.data[0].grammar_function").value("past_participle"))
                .andExpect(jsonPath("$.data[0].gender").doesNotExist())
                .andExpect(jsonPath("$.data[0].id").exists());

        this.mockMvc.perform(get("/terms/{text}", "occurred"))
                .andExpect(jsonPath("$.data[0].title").value("occurred"))
                .andExpect(jsonPath("$.data[0].language").value("en"))
                .andExpect(jsonPath("$.data[0].grammar_function").value("past_participle"))
                .andExpect(jsonPath("$.data[0].gender").doesNotExist())
                .andExpect(jsonPath("$.data[0].id").exists());
    }

    @Test
    public void nonsense_term_HasNoDefinitions() throws Exception {
        this.mockMvc.perform(get("/terms/{text}", "tnhuoasuhesoahtkeboakrb"))
                .andExpect(status().isNotFound());
    }
}
