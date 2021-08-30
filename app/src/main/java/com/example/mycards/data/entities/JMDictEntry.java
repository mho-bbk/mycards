package com.example.mycards.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "jmdict", primaryKeys = {"inner_gloss", "word_id"})
public class JMDictEntry {

    @NonNull
    @JsonProperty("engDef")
    @ColumnInfo(name = "inner_gloss", index = true)
    private String innerGloss;

    @NonNull
    @ColumnInfo(name = "word_id")
    private String wordID;

    //Custom classes using embedded
    @Embedded
    private Kanji kanji = new Kanji();
    @Embedded
    private Kana kana = new Kana();

    @ColumnInfo(name = "gloss_count")
    private int glossCount; //ttl num of gloss

    @ColumnInfo(name = "gloss_order")
    private int glossOrder;

    @ColumnInfo(name = "sense_count")
    private int senseCount; //ttl num of sense

    @ColumnInfo(name = "sense_order")
    private int senseOrder;

    private List<String> sensePosTags;

    public String getInnerGloss() {
        return innerGloss;
    }

    public void setInnerGloss(String gloss) {
        this.innerGloss = gloss;
    }

    public JMDictEntry() {
        this.innerGloss = "";   //non null
        this.wordID = "";   //non null
        this.kana = new Kana();
        this.kanji = new Kanji();
    }

    public JMDictEntry(String gloss, String wordID, Kanji kanji, Kana kana,
                       int glossCount, int glossOrder, int senseCount, int senseOrder,
                       List<String> sensePosTags) {
        this.innerGloss = gloss;
        this.wordID = wordID;
        this.kanji = kanji;
        this.kana = kana;
        this.glossCount = glossCount;
        this.glossOrder = glossOrder;
        this.senseCount = senseCount;
        this.senseOrder = senseOrder;
        this.sensePosTags = sensePosTags;
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

    public List<String> getSensePosTags() {
        return sensePosTags;
    }

    public void setSensePosTags(List<String> sensePosTags) {
        this.sensePosTags = sensePosTags;
    }

    @Override
    public String toString() {
        return "jmdict.JMDictEntry{" +
                "gloss='" + innerGloss + '\'' +
                ", wordID='" + wordID + '\'' +
                ", kanji=" + kanji +
                ", kana=" + kana +
                ", glossCount=" + glossCount +
                ", glossOrder=" + glossOrder +
                ", senseCount=" + senseCount +
                ", senseOrder=" + senseOrder +
                ", sensePosTags" + sensePosTags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JMDictEntry that = (JMDictEntry) o;

        //equality is based on both wordID and innerGloss, mirrors composite PK constraint
        return wordID.equals(that.wordID) && innerGloss.equals(that.innerGloss);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordID);
    }
}
