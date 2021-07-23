package com.example.mycards.jmdict.pojo;

import java.util.List;

public class Kanji {
    private boolean common;
    private String text;    //NEEDED
    private List<String> tags;

    public boolean isCommon() {
        return common;
    }

    public String getText() {
        return text;
    }

    public List<String> getTags() {
        return tags;
    }
}
