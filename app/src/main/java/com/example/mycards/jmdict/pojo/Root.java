package com.example.mycards.jmdict.pojo;

import com.example.mycards.jmdict.pojo.Tag;
import com.example.mycards.jmdict.pojo.Word;

import java.util.List;

public class Root {

    private String version;
    private String dictDate;
    private List<String> dictRevisions;
    private List<Tag> tags;
    private List<Word> words;

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

    public List<Word> getWords() {
        return words;
    }
}