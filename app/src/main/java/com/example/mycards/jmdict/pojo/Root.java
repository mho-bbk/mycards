package com.example.mycards.jmdict.pojo;

import java.util.List;

public class Root {

    private String version;
    private String dictDate;
    private List<String> dictRevisions;
    private List<Tag> tags;
    private List<JMDictWord> words;

    //getters and setters

    public String getVersion() {
        return version;
    }

    public String getDictDate() {
        return dictDate;
    }

    public List<String> getDictRevisions() {
        return dictRevisions;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<JMDictWord> getWords() {
        return words;
    }
}