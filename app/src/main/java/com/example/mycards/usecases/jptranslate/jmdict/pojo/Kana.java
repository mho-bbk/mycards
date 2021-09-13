package com.example.mycards.usecases.jptranslate.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kana kana = (Kana) o;
        return kanaText.equals(kana.kanaText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kanaText);
    }
}
