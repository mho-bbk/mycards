package com.example.mycards.usecases.jptranslate.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kanji kanji = (Kanji) o;
        return kanjiText.equals(kanji.kanjiText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kanjiText);
    }
}
