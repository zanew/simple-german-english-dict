package com.zanewhitney.termlist.domain;

import java.util.Locale;
import java.util.UUID;

public class Term {

    public Term(UUID uuid, String title, Locale locale, GrammarFunction function) {
    }

    public Term(UUID uuid, String title, Locale locale, GrammarFunction function, Gender gender) {
    }

    public Term(UUID uuid, String occurred, Locale us, GrammarFunction pastParticiple, Term definition) {
    }


    public enum GrammarFunction {
        PAST_PARTICIPLE,
        VERB,
        NOUN,
        PROPER_NOUN,
        PREPOSITION,
        ADJECTIVE,
        ADVERB,
        COLLECTIVE_NOUN_PLURAL
    }

    public enum Gender {
        MASCULINE,
        FEMININE,
        NEUTER
    }

    private GrammarFunction grammarFunction;
    private Gender gender;
    private UUID uuid;
    private Term definition;
    private String title;
    private String language;

    public GrammarFunction getGrammarFunction() {
        return grammarFunction;
    }

    public void setGrammarFunction(GrammarFunction grammarFunction) {
        this.grammarFunction = grammarFunction;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public Term getDefinition() {
        return definition;
    }

    public void setDefinition(Term definition) {
        this.definition = definition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
