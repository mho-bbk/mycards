package com.example.mycards.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_answers")
public class UserAnswer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String answer;

    //TODO - we need the below columns in the final db but maybe not this one...
//    @ColumnInfo(name = "related_word")
//    private String relatedWord;
//    private String japanese;

    public UserAnswer(String answer) {
        this.answer = answer;
    }

    //Room will use this later to set the id
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }
}
