package com.example.mycards.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.example.mycards.jmdict.JMDictEntry;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(tableName = "cards", indices = {@Index(value = {"side_a", "side_b"}, unique = true)})
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "side_a")
    private String sideA;

    @ColumnInfo(name = "side_b")
    private String sideB;

    @ColumnInfo(name = "is_shown")
    private boolean isShown;

    public Card(String sideA, String sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.isShown = false;
    }

    //Room will use this later to set the id
    //Also used for our testing purposes
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSideA(String sideA) { this.sideA = sideA; }

    public String getSideA() {
        return sideA;
    }

    public void setSideB(String sideB) { this.sideB = sideB; }

    public String getSideB() {
        return sideB;
    }

    public void setShown(boolean shown) {
        this.isShown = shown;
    }

    public boolean isShown() {
        return isShown;
    }

    @Override
    public @NotNull String toString() {
        return sideA + ", " + sideB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card that = (Card) o;
        return sideA.equals(that.getSideA()) &&
                sideB.equals(that.getSideB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sideA, sideB);
    }
}
