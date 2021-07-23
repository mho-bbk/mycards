package com.example.mycards.jmdict.pojo;

import java.util.List;

public class Kana {
    private boolean common;
    private String text;    //NEEDED
    private List<String> tags;
    private List<String> appliesToKanji;

    public boolean isCommon() {
        return common;
    }

    public String getText() {
        return text;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getAppliesToKanji() {
        return appliesToKanji;
    }
}
