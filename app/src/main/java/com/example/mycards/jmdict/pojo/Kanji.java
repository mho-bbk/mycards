package com.example.mycards.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Kanji {

    @JsonAlias({"common"})
    private boolean commonKanji;
    @JsonAlias({"text"})
    private String kanjiText;    //NEEDED

    public Kanji() {
        this.kanjiText = "";
    }

    public Kanji(String kanji) {
        this.kanjiText = kanji;
    }

    public Kanji(String kanji, boolean bool) {
        this.kanjiText = kanji;
        this.commonKanji = bool;
    }

    public boolean isCommonKanji() {
        return commonKanji;
    }

    public void setCommonKanji(boolean commonKanji) {
        this.commonKanji = commonKanji;
    }

    public void setKanjiText(String kanjiText) {
        this.kanjiText = kanjiText;
    }

    public String getKanjiText() {
        return kanjiText;
    }

    @Override
    public String toString() {
        return "Kanji{" +
                "text='" + kanjiText + '\'' +
                '}';
    }
}
