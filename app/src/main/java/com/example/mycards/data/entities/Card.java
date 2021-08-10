package com.example.mycards.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "repeat")
    private boolean repeat;

    @ColumnInfo(name = "deck_seed")
    private String deckSeed;    //this is to help us with user getting deck back

    @Ignore
    public Card(String sideA, String sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.repeat = false;
        this.deckSeed = ""; //avoid NPE
    }

    //Overload constructor fn
    public Card(String sideA, String sideB, String deckSeed) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.repeat = false;
        if(deckSeed == null) {
            this.deckSeed = ""; //avoid NPE
        } else {
            this.deckSeed = deckSeed;
        }
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

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public String getDeckSeed() {
        return deckSeed;
    }

    public void setDeckSeed(String deckSeed) {
        this.deckSeed = deckSeed;
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
