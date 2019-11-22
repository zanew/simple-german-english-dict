package com.zanewhitney.termlist;

import com.zanewhitney.termlist.domain.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestUtilities {

/*    public static Dictionary<Term, Term> getThreeDefinitions() {
        Dictionary<Term, Term> terms = new Dictionary<Term, Term>();

        Term geschehen = new Term(UUID.randomUUID(), "geschehen", Locale.GERMANY, "verb");
        Term geschehenNoun = new Term(UUID.randomUUID(), "Geschehen", Locale.GERMANY, "noun", "n");

        Term occurred = new Term(UUID.randomUUID(), "occurred", Locale.US, "verb");
        Term happened = new Term(UUID.randomUUID(), "happened", Locale.US, "verb");
        Term events = new Term(UUID.randomUUID(), "events", Locale.US, "collective noun", "plural");

        terms.put(occurred, geschehen);
        terms.put(happened, geschehen);
        terms.put(events, geschehenNoun);

        return terms;
    }
    */

    public static List<Term> getThreeDefinitions2() {
        Term geschehen = new Term(java.util.UUID.randomUUID(), "geschehen", Locale.GERMANY, Term.GrammarFunction.PAST_PARTICIPLE);
        Term geschehenNoun = new Term(java.util.UUID.randomUUID(), "Geschehen", Locale.GERMANY, Term.GrammarFunction.NOUN, Term.Gender.NEUTER);

        Term occurred = new Term(java.util.UUID.randomUUID(), "occurred", Locale.US, Term.GrammarFunction.PAST_PARTICIPLE, geschehen.getId());
        Term happened = new Term(java.util.UUID.randomUUID(), "happened", Locale.US, Term.GrammarFunction.PAST_PARTICIPLE, geschehen.getId());
        Term events = new Term(java.util.UUID.randomUUID(), "events", Locale.US, Term.GrammarFunction.COLLECTIVE_NOUN_PLURAL, geschehenNoun.getId());

        List<Term> Terms = new ArrayList<>();
        Terms.add(occurred);
        Terms.add(happened);
        Terms.add(events);

        return Terms;
    }
}
