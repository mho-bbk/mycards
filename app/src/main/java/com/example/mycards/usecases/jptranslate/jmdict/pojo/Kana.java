package com.example.mycards.usecases.jptranslate.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Kana {
    @JsonAlias({"text"})
    private String kanaText;    //NEEDED

    public Kana() {
        this.kanaText = "";
    }

    public Kana(String kana) {
        this.kanaText = kana;
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
