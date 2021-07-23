package com.example.mycards.jmdict;

import com.example.mycards.jmdict.pojo.Tag;
import com.example.mycards.jmdict.pojo.Word;

import java.util.List;

public class JMDictJSONRoot {

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

    //Other DTO as inner classes here for the moment
//    public class Tag {
//        private String key;
//        private String tagValue;
//
//        public String getKey() {
//            return key;
//        }
//
//        public String getTagValue() {
//            return tagValue;
//        }
//    }

//    public class Word {
//        private String id;
//        private List<Kanji> kanji;
//        private List<Kana> kana;
//        //One word should only have one sense
//        private Sense sense;
//
//        public String getId() {
//            return id;
//        }
//
//        public List<Kanji> getKanji() {
//            return kanji;
//        }
//
//        public List<Kana> getKana() {
//            return kana;
//        }
//
//        public Sense getSense() {
//            return sense;
//        }
//    }

//    public class Kanji {
//        private boolean common;
//        private String text;    //NEEDED
//        private List<String> tags;
//
//        public boolean isCommon() {
//            return common;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public List<String> getTags() {
//            return tags;
//        }
//    }

//    public class Kana {
//        private boolean common;
//        private String text;    //NEEDED
//        private List<String> tags;
//        private List<String> appliesToKanji;
//
//        public boolean isCommon() {
//            return common;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public List<String> getTags() {
//            return tags;
//        }
//
//        public List<String> getAppliesToKanji() {
//            return appliesToKanji;
//        }
//    }

//    public class Sense {
//        private List<String> pos;
//        private List<String> appliesToKanji;
//        private List<String> appliesToKana;
//        private List<String> related;
//        private List<String> antonym;
//        private List<String> field;
//        private List<String> dialect;
//        private List<String> misc;
//        private List<String> info;
//        private List<Object> languageSource;    //Object for now as doc makes it misc and we don't need it
//        private List<Gloss> gloss; //NEEDED
//
//        public List<String> getAppliesToKanji() {
//            return appliesToKanji;
//        }
//
//        public List<String> getAppliesToKana() {
//            return appliesToKana;
//        }
//
//        public List<Gloss> getGloss() {
//            return gloss;
//        }
//    }

//    public class Gloss {
//        private String type;
//        private String lang;    //always eng in this dataset
//        private String text;    //NEEDED
//
//        public String getType() {
//            return type;
//        }
//
//        public String getLang() {
//            return lang;
//        }
//
//        public String getText() {
//            return text;
//        }
//    }
//}
