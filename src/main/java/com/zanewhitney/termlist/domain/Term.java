package com.zanewhitney.termlist.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "terms")
public class Term {

    // builders
    public Term() {
        this.id = UUID.randomUUID().toString();
    }

    public Term title(String title) {
        this.title = title;
        return this;
    }

    public Term language(Locale locale) {
        this.language = locale.getLanguage();
        return this;
    }

    public Term grammarFunction(GrammarFunction function) {
        this.grammarFunction = function;
        return this;
    }

    public Term gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Term definition(String definition) {
        this.definition = definition;
        return this;
    }


    public enum GrammarFunction {
        PAST_PARTICIPLE,
        VERB,
        NOUN,
        PROPER_NOUN,
        PREPOSITION,
        ADJECTIVE,
        ADVERB,
        COLLECTIVE_NOUN_PLURAL;

        @JsonValue
        public String lowerCase() {
            return this.toString().toLowerCase();
        }
    }

    public enum Gender {
        MASCULINE,
        FEMININE,
        NEUTER
    }

    private GrammarFunction grammarFunction;
    private Gender gender;
    @Id
    private String id;
    private String definition;
    private String title;
    private String language;

    @Column(name = "grammar_function", nullable = false)
    @JsonProperty("grammar_function")
    public GrammarFunction getGrammarFunction() {
        return grammarFunction;
    }

    public void setGrammarFunction(GrammarFunction grammarFunction) {
        this.grammarFunction = grammarFunction;
    }

    @Column(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    @Column(name = "definition")
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "language", nullable = false)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
