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

    public static List<Term> getThreeDefinitions() {
        Term geschehen = new Term().title("geschehen").language(Locale.GERMANY).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE);

        Term occurred = new Term().title("occurred").language(Locale.US).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE).definition(geschehen.getId());
        Term happened = new Term().title("happened").language(Locale.US).grammarFunction(Term.GrammarFunction.PAST_PARTICIPLE).definition(geschehen.getId());

        Term geschehenNoun = new Term().title("Geschehen").language(Locale.GERMANY).grammarFunction(Term.GrammarFunction.NOUN).gender(Term.Gender.NEUTER);
        Term events = new Term().title("events").language(Locale.US).grammarFunction(Term.GrammarFunction.COLLECTIVE_NOUN_PLURAL).definition(geschehenNoun.getId());
        geschehen.setDefinition(occurred.getId());

        List<Term> terms = new ArrayList<>();
        terms.add(occurred);
        terms.add(geschehen);
        terms.add(happened);
        terms.add(events);
        terms.add(geschehenNoun);

        return terms;
    }
}
