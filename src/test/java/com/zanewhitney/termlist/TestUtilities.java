package com.zanewhitney.termlist;

import com.zanewhitney.termlist.domain.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestUtilities {
    public static List<Term> getDefinitions() {
        return TestUtilities.getDefinitions(5);
    }

    public static List<Term> getDefinitions(int resultsPerPage) {
        Term geschehen = new Term().title("geschehen").language(Locale.GERMANY).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE);

        Term occurred = new Term().title("occurred").language(Locale.US).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE).definition(geschehen.getId());
        Term happened = new Term().title("happened").language(Locale.US).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE).definition(geschehen.getId());

        Term geschehenNoun = new Term().title("Geschehen").language(Locale.GERMANY).grammarFunction(Term.GrammarFunction.NOUN).gender(Term.Gender.NEUTER);
        Term events = new Term().title("events").language(Locale.US).grammarFunction(Term.GrammarFunction.COLLECTIVE_NOUN_PLURAL).definition(geschehenNoun.getId());
        geschehen.setDefinition(occurred.getId());

        List<Term> terms = new ArrayList<>();
        terms.add(occurred);
        terms.add(happened);
        terms.add(events);
        terms.add(geschehen);
        terms.add(geschehenNoun);

        List<Term> termsToReturn = new ArrayList<>();
        for (int i = 0; i < resultsPerPage; i++) {
            termsToReturn.add(terms.get(i));
        }

        return termsToReturn;
    }
}
