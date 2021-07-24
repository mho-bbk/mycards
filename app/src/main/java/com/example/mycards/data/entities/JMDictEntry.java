package com.example.mycards.data.entities;

import com.example.mycards.jmdict.JMDictJSONRoot;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;

import java.util.Objects;

//TODO - turn this into Room Entity using annotations?
public class JMDictEntry {
    private String engDef;  //this is referred to as gloss in the file
    private Kanji kanji = new Kanji();
    private Kana kana = new Kana();
    private String wordID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JMDictEntry that = (JMDictEntry) o;
        return wordID.equals(that.wordID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordID);
    }
}
