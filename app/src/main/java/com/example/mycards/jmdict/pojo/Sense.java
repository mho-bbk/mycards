package com.example.mycards.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class Sense {

    @JsonAlias("partOfSpeech")
    private List<String> pos;

    private List<String> appliesToKanji;
    private List<String> appliesToKana;
    private List<String> related;
    private List<String> antonym;
    private List<String> field;
    private List<String> dialect;
    private List<String> misc;
    private List<String> info;
    private List<Object> languageSource;    //Object for now as doc makes it misc and we don't need it
    private List<Gloss> gloss; //NEEDED

    public List<String> getAppliesToKanji() {
        return appliesToKanji;
    }

    public List<String> getAppliesToKana() {
        return appliesToKana;
    }

    public List<Gloss> getGloss() {
        return gloss;
    }
}
