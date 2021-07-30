package com.example.mycards.jmdict.pojo;

import java.util.List;

public class JMDictWord {
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
