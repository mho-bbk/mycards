package com.example.mycards.jmdict.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Tag {

    @JsonAlias({"X", "Buddh", "chem", "chn", "col", "comp", "derog", "exp", "fam", "food", "geom",
            "id", "ling", "m-sl", "math", "mil", "num", "oK", "obs", "obsc", "ok", "physics",
            "sl","kyb", "osb", "ksb", "ktb", "tsb", "thb", "tsug", "kyu", "rkb", "nab", "hob",
            "vulg", "archit", "astron", "baseb", "biol", "bot", "bus", "econ", "engr", "finc",
            "geol", "law", "mahj", "med", "music", "Shinto", "shogi", "sports", "sumo", "zool",
            "joc", "anat", "Christn", "net-sl", "hist", "litf", "surname", "place", "company",
            "product", "work", "person", "given", "station", "organization"})
    private String key;

    private String tagValue;

    public String getKey() {
        return key;
    }

    public String getTagValue() {
        return tagValue;
    }
}
