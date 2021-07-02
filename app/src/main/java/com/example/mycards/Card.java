package com.example.mycards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Class depicting a flashcard object
 * Would use a record if this was in Java 16
 */
public class Card {

    private final String sideA;
    private final String sideB;
    private boolean shown;

    public Card(String sideA, String sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.shown = false; //when a card is initialised, its default behaviour is that it hasn't been shown
    }

    @Override
    public int hashCode() {
        return Objects.hash(sideA, sideB);
    }

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Card)) {
            return false;
        } else {
            Card other = (Card) obj;
            return Objects.equals(sideA, other.getSideA())
                    && Objects.equals(sideB, other.getSideB());
        }
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Side A: " + sideA + ", Side B: " + sideB;
    }

    public String getSideA() {
        return sideA;
    }

    public String getSideB() {
        return sideB;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }
}
