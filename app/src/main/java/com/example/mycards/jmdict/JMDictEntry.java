package com.example.mycards.jmdict;

import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;

import java.util.Objects;

public class JMDictEntry {
    private String engDef;  //this is referred to as gloss in the file
    private Kanji kanji = new Kanji();
    private Kana kana = new Kana();
    private String wordID;

    private int glossCount; //ttl num of gloss
    private int glossOrder;
    private int senseCount; //ttl num of sense
    private int senseOrder;

    public String getEngDef() {
        return engDef;
    }

    public void setEngDef(String engDef) {
        this.engDef = engDef;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public Kana getKana() {
        return kana;
    }

    public void setKana(Kana kana) {
        this.kana = kana;
    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(String wordID) {
        this.wordID = wordID;
    }

    public void setGlossCount(int glossCount) {
        this.glossCount = glossCount;
    }

    public void setGlossOrder(int glossOrder) {
        this.glossOrder = glossOrder;
    }

    public void setSenseCount(int senseCount) {
        this.senseCount = senseCount;
    }

    public void setSenseOrder(int senseOrder) {
        this.senseOrder = senseOrder;
    }

    public int getGlossCount() {
        return glossCount;
    }

    public int getGlossOrder() {
        return glossOrder;
    }

    public int getSenseCount() {
        return senseCount;
    }

    public int getSenseOrder() {
        return senseOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JMDictEntry that = (JMDictEntry) o;

        //equality is based on wordID to avoid the same Japanese term appearing for diff glosses
        return wordID.equals(that.wordID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordID);
    }
}
