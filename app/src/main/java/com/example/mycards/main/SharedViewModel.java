package com.example.mycards.main;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.base.callbacks.Result;
import com.example.mycards.base.callbacks.UseCaseCallback;
import com.example.mycards.data.entities.Deck;
import com.example.mycards.usecases.UseCaseManager;
import com.example.mycards.data.entities.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;


@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private static final String TAG = "SharedViewModel";

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private UseCaseManager useCaseManager;

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    private final List<String> userInputListCopy = new ArrayList<>();   //Used to return decks
    //Observer of userInputs
    private final Observer<List<String>> inputObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(List<String> input) {
            //NEW IMPL: Save copy of user inputs. This will be used to return decks.
            //Clear inputList everytime this observer is activated (ie everytime new input is entered)
            userInputListCopy.clear();
            userInputListCopy.addAll(input);

            //Deploy use cases via the Manager Mediator and request callback when done
            //Should be on Main thread
            useCaseManager.checkInputListThenRun(input, new UseCaseCallback<Boolean>() {
                @Override
                public void onComplete(Result<Boolean> result) {
                    if(result instanceof Result.Success) {
                        Boolean success = ((Result.Success<Boolean>) result).getData(); //False if 0 cards inserted from userInput
                        if(success) {
                            mainHandler.post(() ->
                                    cardsInRepoReady.setValue(true)
                            );
                        } else {
                            mainHandler.post(() ->
                                    cardsInVMReady.setValue(false)  //Same result as if there is an error
                            );
                        }
                    } else {
                        //show on UI that no cards could be found
                        // send signal to waiting CardFragment to move to separate 'error' fragment?
                        mainHandler.post(() ->
                                cardsInVMReady.setValue(false)
                        );
                    }
                }
            });
        }
    };


    public MutableLiveData<Boolean> cardsInRepoReady = new MutableLiveData<>();
    public final LiveData<List<Card>> cardTransformation =
            Transformations.switchMap(cardsInRepoReady,
                    (ready) -> useCaseManager.getCards(userInputListCopy)); //only comes here when ready is true
    //Observer of cardTransformation
    private final Observer<List<Card>> cardObserver = new Observer<List<Card>>() {
        @Override
        public void onChanged(List<Card> cards) {
            setUpDeckInVM(cards);
//            useCaseManager.createDeck(userInputListCopy);   //Boolean is returned here but not used
        }
    };

    public MutableLiveData<Boolean> cardsInVMReady = new MutableLiveData<>();

    private Iterator<Card> deckIterator;
    private Card currentCard = new Card("", "");    //blank card to initiate

    //TODO
    // Possible states of readiness (and impacted workflows)
    //cardsInRepoReady - cards in repo ready/not (so VM and UI can begin to use them) <- should this be a regular old observer (not LiveData)?
    //cardsInVMReady - not always necess depending on impl - so UI can use them (from the VM) <- not needed if NEWIMPL2?
    //cardPosition - where in the deck are we out of the ttl num of cards <- this should be set by the CDF
    //deckStarted - we have started a deck (not necessary?)
    //deckFinished - we have finished a deck

    private LiveData<List<Deck>> decksVMCopy;

    @Inject
    public SharedViewModel(UseCaseManager useCaseManager) {
        this.useCaseManager = useCaseManager;

        //Observe the LiveData ie user input, passing in an observer that does the logic.
        userInputs.observeForever(inputObserver);
        cardTransformation.observeForever(cardObserver);

        //Instantiate decks
        this.decksVMCopy = getDecks();
    }

    /**
     * Helper method. Sets up deckIterator and currentCard fields when observer on userAnswers gets all cards.
     * @param allCards List of Card based on user input
     */
    private void setUpDeckInVM(List<Card> allCards) {
        try {
            deckIterator = allCards.iterator();
            if (deckIterator.hasNext()) {
                currentCard = deckIterator.next();
            }
            cardsInVMReady.setValue(true);
        } catch(NullPointerException e) {
            Log.d(TAG, Thread.currentThread().getName() + ", " + e.getMessage() +
                    "\nsetUpDeck() has thrown NPE");
            cardsInVMReady.setValue(false);
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
                //TODO - run finished procedure: reset currentCard,
                // trigger CardDisplayFragment to go to Finished page
                currentCard = new Card("Finished deck", "Finished deck");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, Thread.currentThread().getName() + ", " + e.getMessage() +
                    "\ngetNextCard() has thrown NPE");
        }
        return currentCard;
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

    public void deleteAllCards() {
        useCaseManager.deleteAllCards();
    }

    public LiveData<List<Deck>> getDecks() {
        return useCaseManager.getDecks();
    }

    public void deleteDeck(Deck deck) {
        useCaseManager.deleteDeck(deck);
        //call getDecks to refresh? This notifies the observer in DeckAdapter?
        this.decksVMCopy = getDecks();
    }

    public void deleteAllDecks() {
        useCaseManager.deleteAllDecks();
        this.decksVMCopy = getDecks();
    }

    @Override
    protected void onCleared() {
        userInputs.removeObserver(inputObserver);
        cardTransformation.removeObserver(cardObserver);
        super.onCleared();
    }
}