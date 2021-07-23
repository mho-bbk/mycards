package com.example.mycards.jmdict.pojo;

import com.example.mycards.jmdict.JMDictJSONRoot;

import java.util.List;

public class Word {
    private String id;
    private List<Kanji> kanji;
    private List<Kana> kana;
    //One word should only have one sense
    private List<Sense> sense;

    public String getId() {
        return id;
    }

    public List<Kanji> getKanji() {
        return kanji;
    }

    public List<Kana> getKana() {
        return kana;
    }

    public List<Sense> getSense() {
        return sense;
    }
}
