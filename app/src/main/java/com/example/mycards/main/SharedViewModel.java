package com.example.mycards.main;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.usecases.CreateAndGetCardUseCase;
import com.example.mycards.data.entities.Card;
import com.example.mycards.usecases.GetJpWordsUseCase;
import com.example.mycards.usecases.GetSimilarWordsUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private GetSimilarWordsUseCase similarWordsUseCase;
    private GetJpWordsUseCase jpWordsUseCase;
    private CreateAndGetCardUseCase cardUseCase;

    private HashMap<String, String> engToJpWords = new HashMap<>();
    private Iterator<Card> deckIterator;
    private Card currentCard = new Card("", "");    //blank card to initiate

    //Creating an observer
    private final Observer<List<Card>> observer = new Observer<List<Card>>() {
        @Override
        public void onChanged(List<Card> cards) {
            setUpDeck(cards);
        }
    };

//    private final Observer<JMDictEntry> jmDictEntryObserver = new Observer<JMDictEntry>() {
//        @Override
//        public void onChanged(JMDictEntry dictEntry) {
//            //Get result of the LiveData here and do something
//            engToJpWords.put(dictEntry.getInnerGloss(), jpWordsUseCase.formatKanjiAndKana(dictEntry));
//        }
//    };

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    public final LiveData<List<Card>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
        //Convert input String to Card class here
        //Deploy use cases here
//        for (String s: inputList) {
            //semantic search here using DatamuseAPI
//            List<String> simWords = similarWordsUseCase.run(s);

        //Test so we can stop needlessly calling API...
        List<String> fakeSimWords = new ArrayList<>(List.of("to speed up", "to fail to notice", "inspection"));

        //get the Jp translations
        for (String word: fakeSimWords) {
            jpWordsUseCase.run(word);   //returns bool
        }
            //insert Card into db via the usecase
            cardUseCase.run(jpWordsUseCase.getEngToJpMap());  //returns a bool if success that isn't captured anywhere
//        }
        return cardUseCase.getAllCards();
    });

    @Inject
    public SharedViewModel(GetSimilarWordsUseCase similarWordsUseCase,
                           GetJpWordsUseCase jpWordsUseCase,
                           CreateAndGetCardUseCase cardUseCase) {
        this.similarWordsUseCase = similarWordsUseCase;
        this.jpWordsUseCase = jpWordsUseCase;
        this.cardUseCase = cardUseCase;

        //Observe the LiveData ie user input, passing in the global observer.
        userAnswers.observeForever(observer);
//        jpWordsUseCase.setObserver(jmDictEntryObserver);

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
        return new Card(inputWord, inputWord + " in Japanese");
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

    //public accessor to this usecase, enables deleteCards to be called from a fragment
    //TODO - refactor so this is private to avoid misuse of run()
    public CreateAndGetCardUseCase getCardUseCase() {
        return cardUseCase;
    }

    @Override
    protected void onCleared() {
//        jpWordsUseCase.removeObserver(jmDictEntryObserver);
//        jpWordsUseCase.removeObserver();
        userAnswers.removeObserver(observer);
        super.onCleared();
    }
}