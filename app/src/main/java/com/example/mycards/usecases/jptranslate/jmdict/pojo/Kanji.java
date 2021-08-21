package com.example.mycards.usecases.jptranslate.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Kanji {

    @JsonAlias({"text"})
    private String kanjiText;    //NEEDED

    public Kanji() {
        this.kanjiText = "";
    }

    public Kanji(String kanji) {
        this.kanjiText = kanji;
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
