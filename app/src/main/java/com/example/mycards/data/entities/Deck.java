package com.example.mycards.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "decks", indices = {@Index(value = {"deck_name"}, unique = true)})
public class Deck {

    @PrimaryKey(autoGenerate = true)
    public int deckId;

    @NonNull
    @ColumnInfo(name = "deck_name")
    private String deckName;

    public Deck(@NotNull String deckName) { this.deckName = deckName; }

    @Ignore
    public Deck(List<String> inputWords) {
        this.deckName = createName(inputWords);
    }

    public void setDeckName(String deckName) { this.deckName = deckName; }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getDeckId() {
        return deckId;
    }

    public @NotNull String getDeckName() {
        return deckName;
    }

    //Returns String printout of the inputWords to display as deckName in format:
    //"word1, word2, word3"
    private String createName(List<String> inputWords) {
        StringBuilder name = new StringBuilder("");

        for (int i = 0; i < inputWords.size() - 1; i++) {
            name.append(inputWords.get(i));
            name.append(", ");
        }

        //add last word on separately so doesn't have ", "
        name.append(inputWords.get(inputWords.size()-1));

        return name.toString();
    }

    //Public method to rebuild the inputList
    //This should help to search for the cards
    public static List<String> rebuildInputList(String deckName) {
        String[] splitString = deckName.split(", ");
        return new ArrayList<>(Arrays.asList(splitString));
    }

    @Override
    public @NotNull String toString() {
        return "Deck{" +
                "deckId=" + deckId +
                ", deckName='" + deckName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(deckName, deck.deckName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckName);
    }
}
