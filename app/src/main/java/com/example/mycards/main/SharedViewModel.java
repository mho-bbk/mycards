package com.example.mycards.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.jmdict.JMDictEntry;
import com.example.mycards.jmdict.JMDictEntryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private CardRepository cardRepository;
    private JMDictEntryBuilder entryBuilder;

    private Iterator<Card> deckIterator;
    private Card currentCard = new Card("", "");    //blank card to initiate

    //Creating an observer
    private final Observer<List<Card>> observer = new Observer<List<Card>>() {
        @Override
        public void onChanged(List<Card> cards) {
            setUpDeck(cards);
        }
    };

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    public final LiveData<List<Card>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
        //Convert input String to CardEntity class here
        for (String s: inputList) {
            Card input = createCard(s);
            upsert(input);
        }
        return getAllCards();
    });

    public SharedViewModel(CardRepository repository) {
        this.cardRepository = repository;

        //Observe the LiveData, passing in the global observer.
        userAnswers.observeForever(observer);

    }

    /**
     * Method called by the FragmentActivity to pass the correct resource to the VM
     * @param input stream representing the dictionary file
     */
    public void loadJMDict(InputStream input) {
        try {
            entryBuilder = JMDictEntryBuilder.getInstance(input);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Helper method. Sets up deckIterator and currentCard fields when user input is received.
     * @param allCards List of Card based on user input
     */
    private void setUpDeck(List<Card> allCards) {
        try {
            deckIterator = allCards.iterator();
            if (deckIterator.hasNext()) {
                currentCard = deckIterator.next();
            }
        } catch(NullPointerException e) {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Public method used by MainFragment to pass user input to this ViewModel.
     * @param allUserInput List of user input received as String
     */
    public void setUserInputs(List<String> allUserInput) {
        userInputs.setValue(allUserInput);
    }

    /**
     * Public method used by CardDisplayFragment to get the card that needs to be displayed on the UI.
     * @return currentCard according to the deckIterator
     */
    public Card getCurrentCard() {
        return this.currentCard;
    }

    /**
     * Public method used by CardDisplayFragment to move to the next Card.
     * Iterates the deckIterator and resets currentCard.
     * @return currentCard according to deckIterator
     */
    public Card getNextCard() {
        if(deckIterator.hasNext()) {
            currentCard = deckIterator.next();
        } else {
            currentCard = new Card("Finished deck", "Finished deck");
        }
        return currentCard;
    }

    /**
     * Helper method to create cards using the JMDict JSON file
     * @param inputWord
     * @return
     */
    private Card createCard(String inputWord) {
        List<JMDictEntry> entries = entryBuilder.getJMDictEntries(inputWord);
        if(entries.isEmpty()) {
            return new Card("Blank", "Blank");
        } else {
            //Return the first word as list should be ordered (most rel first)
            JMDictEntry jmde = entries.get(0);
            String jWord;
            if(jmde.getKanji().getText().equals("")) {
                jWord = jmde.getKana().getText();
            } else {
                jWord = jmde.getKanji().getText() + " (" + jmde.getKana().getText() + ")";
            }
            return new Card(inputWord, jWord);
        }
    }

    //TODO - could you do a repeat function with resetDeck() and setUserInputs?
//    private Queue<Card> repeatDeck = new LinkedList<>();
//
//    public Queue<Card> getRepeatDeck() {
//        return repeatDeck;
//    }
//
//    public void addToRepeatDeck(Card card) {
//        this.repeatDeck.add(card);
//    }
//
//    public void setCardIteratorToRepeatDeck() {
//        this.cardIterator = repeatDeck.iterator();
//    }

    //**REPOSITORY/DAO METHODS**
    public LiveData<List<Card>> getAllCards() { return cardRepository.getAllCards(); }

    public void upsert(Card card) {
        cardRepository.upsert(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAllCards();
    }

    @Override
    protected void onCleared() {
        userAnswers.removeObserver(observer);
        this.deleteAllCards();   //TODO - this doesn't seem to work. Why? Related to repositories and persistence?
        super.onCleared();
    }
}