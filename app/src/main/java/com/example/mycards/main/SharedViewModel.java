package com.example.mycards.main;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private static final String TAG = "SharedViewModel";    //for use in Logcat

    private GetSimilarWordsUseCase similarWordsUseCase;
    private GetJpWordsUseCase jpWordsUseCase;
    private CreateAndGetCardUseCase cardUseCase;

    public MutableLiveData<Boolean> runAllUseCasesSuccessful = new MutableLiveData<>();

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
        //Deploy use cases here
        runUseCases(inputList);
        return cardUseCase.getAllCards();   //may return nothing - TODO handle empty card db (TEST)
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

    private void runUseCases(List<String> inputList) {
        for (String s: inputList) {
            //semantic search here using DatamuseAPI
            List<String> simWords = similarWordsUseCase.run(s);
            //Test so we can stop needlessly calling API...
//                List<String> fakeSimWords = new ArrayList<>(List.of("to speed up", "to fail to notice", "inspection"));

            //get the Jp translations
            for (String word: simWords) {
                jpWordsUseCase.run(word);   //returns bool
            }

            //insert Card into db via the usecase
            cardUseCase.run(jpWordsUseCase.getEngToJpMap());  //returns a bool if success that isn't captured anywhere
            Log.d(TAG, "Returning cardUseCase.getAllCards() within userAnswers Transformations.switchMap...");
        }
        runAllUseCasesSuccessful.setValue(true);
    }

    /**
     * Helper method. Sets up deckIterator and currentCard fields when observer on userAnswers gets all cards.
     * @param allCards List of Card based on user input
     */
    private void setUpDeck(List<Card> allCards) {
        try {
            deckIterator = allCards.iterator();
            if (deckIterator.hasNext()) {
                currentCard = deckIterator.next();
            }
        } catch(NullPointerException e) {
            Log.d(TAG, e.getMessage() + "\nsetUpDeck() has thrown NPE");
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
        try {
            if (deckIterator.hasNext()) {
                currentCard = deckIterator.next();
            } else {
                currentCard = new Card("Finished deck", "Finished deck");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, e.getMessage() + "\ngetNextCard() has thrown NPE");
        }
        return currentCard;
    }

    public Boolean isRunAllUseCasesSuccessful() {
        return runAllUseCasesSuccessful.getValue();
    }

    public void setRunAllUseCasesSuccessful(Boolean success) {
        runAllUseCasesSuccessful.setValue(success);
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

    public boolean deleteAllCards() {
        cardUseCase.deleteAllCards();
        return true;
    }

    @Override
    protected void onCleared() {
//        jpWordsUseCase.removeObserver(jmDictEntryObserver);
        jpWordsUseCase.removeObserver();
        userAnswers.removeObserver(observer);
        super.onCleared();
    }
}