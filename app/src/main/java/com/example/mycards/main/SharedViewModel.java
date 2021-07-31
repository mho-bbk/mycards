package com.example.mycards.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.datamuse.DatamuseClient;
import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.example.mycards.jmdict.JMDictEntry;
import com.example.mycards.jmdict.JMDictEntryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedViewModel extends ViewModel {

    private CardRepository cardRepository;
    private JMDictEntryBuilder entryBuilder;
    private List<DatamuseWord> datamuseWords;

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
        //Convert input String to Card class here
        for (String s: inputList) {
            Card input = createCard(s);
            upsert(input);
//            //semantic search here using DatamuseAPI
            startSemanticSearch(s);
        }
        return getAllCards();
    });

    private void startSemanticSearch(String s) {
        Call<List<DatamuseWord>> call = DatamuseClient.getInstance()
                .getDatamuseAPIService()
                .getMaxSingleSearchResults(s, 3);

        call.enqueue(new Callback<List<DatamuseWord>>() {
            @Override
            public void onResponse(Call<List<DatamuseWord>> call, Response<List<DatamuseWord>> response) {
                if(response.isSuccessful()) {
                    datamuseWords = response.body();
                    setDatamuseWordsAndCreateCards(datamuseWords);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DatamuseWord>> call, Throwable t) {
                System.out.println("An error occurred with testDatamuseAPIService() method");
                t.printStackTrace();
            }
        });
    }

    //TODO - Violates SRP so separate
    private void setDatamuseWordsAndCreateCards(List<DatamuseWord> words) {
        this.datamuseWords = words;
        for (DatamuseWord word: datamuseWords) {
            Card card = createCard(word.getWord());
            upsert(card);
        }
    }

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
            entryBuilder = JMDictEntryBuilder.getInstance(input);   //TODO - pass executor to builder?
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
        return entryBuilder.getFirstEntryAsCard(inputWord);
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