package com.example.mycards.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Kana {
    @JsonAlias({"common"})
    private boolean commonKana;
    @JsonAlias({"text"})
    private String kanaText;    //NEEDED

    public Kana() {
        this.kanaText = "";
    }

    public Kana(String kana) {
        this.kanaText = kana;
    }

    public Kana(String kana, boolean bool) {
        this.kanaText = kana;
        this.commonKana = bool;
    }

    public boolean isCommonKana() {
        return commonKana;
    }

    public void setCommonKana(boolean commonKana) {
        this.commonKana = commonKana;
    }

    public void setKanaText(String kanaText) {
        this.kanaText = kanaText;
    }

    public String getKanaText() {
        return kanaText;
    }

    @Override
    public String toString() {
        return "Kana{" +
                "text='" + kanaText + '\'' +
                '}';
    }
}
