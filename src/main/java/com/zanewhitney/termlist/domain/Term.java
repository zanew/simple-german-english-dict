package com.zanewhitney.termlist.domain;

import javax.persistence.*;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "terms")
public class Term {

    public Term(UUID uuid, String title, Locale locale, GrammarFunction function) {
    }

    public Term(UUID uuid, String title, Locale locale, GrammarFunction function, Gender gender) {
    }

    public Term(UUID uuid, String occurred, Locale us, GrammarFunction pastParticiple, UUID definition) {
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
    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID definition;
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

    public void setId(java.util.UUID id) {
        this.uuid = id;
    }

    public UUID getDefinition() {
        return definition;
    }

    public void setDefinition(UUID definition) {
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
