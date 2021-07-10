package com.example.mycards.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "user_answers", indices = {@Index(value = "answer", unique = true)})
public class UserAnswer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String answer;

    //TODO - we need the below columns in the final db but maybe not this one...
//    @ColumnInfo(name = "related_word")
//    private String relatedWord;
//    private String japanese;

    public UserAnswer(String answer) {
        this.answer = answer;
    }

    //Room will use this later to set the id
    //Also used for our testing purposes
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswer that = (UserAnswer) o;
        return id == that.getId() &&
                answer.equals(that.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer);
    }
}
