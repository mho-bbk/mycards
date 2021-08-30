package com.example.mycards.server.datamuse.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatamuseWord {

    private String word = "";   //Essential, not null
    private int score = 0;      //Essential, not null
    private List<String> tags = new ArrayList<>();

    public DatamuseWord() {
        //no args constructor
    }

    public DatamuseWord(String word, int score) {
        this.word = word;
        this.score = score;
    }

    public DatamuseWord(String word, int score, List<String> tags) {
        this.word = word;
        this.score = score;
        this.tags = tags;
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }

    public List<String> getTags() {
        return tags;
    }

    //Tags not essential. Can set these later.
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    //Equality is defined by if the String word and the Datamuse API score is the same
    //Tags do not define equality.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatamuseWord that = (DatamuseWord) o;
        return score == that.score &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, score);
    }

    @Override
    public String toString() {
        return "DatamuseWord{" +
                "word='" + word + '\'' +
                ", score=" + score +
                ", tags=" + tags +
                '}';
    }
}
