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
import com.example.mycards.usecases.UseCaseManager;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.data.entities.Card;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;


@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private static final String TAG = "SharedViewModel";    //for use in Logcat
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private UseCaseManager useCaseManager;
//    private GetSimilarWordsUseCase similarWordsUseCase;
//    private GetJpWordsUseCase jpWordsUseCase;
//    private CreateAndGetCardUseCase cardUseCase;

    private String currentDeckSeed;

    public MutableLiveData<Boolean> cardsInVMReady = new MutableLiveData<>();
    public MutableLiveData<Boolean> cardsInRepoReady = new MutableLiveData<>();
    public final LiveData<List<Card>> cardTransformation =
            Transformations.switchMap(cardsInRepoReady, (ready) -> useCaseManager.getCards(currentDeckSeed));

    private Iterator<Card> deckIterator;
    private Card currentCard = new Card("", "");    //blank card to initiate

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();

    //NEW IMPL
    private final Observer<List<String>> inputObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(List<String> input) {
            //Set the currentDeckSeed which is owned by VM
            setCurrentDeckSeed(input);
            //Deploy use cases via the Manager Mediator and request callback when done
            //Should be on Main thread
            useCaseManager.runAllUseCases(input, currentDeckSeed, new UseCaseCallback<Boolean>() {
                @Override
                public void onComplete(Result<Boolean> result) {
                    if(result instanceof Result.Success) {
                        Boolean success = ((Result.Success<Boolean>) result).getData();
                        mainHandler.post( () ->
                                cardsInRepoReady.setValue(success) //Is this ever false?
                        );
                    } else {
                        //show on UI that no cards could be found
                        // send signal to waiting CardFragment to move to sep 'error' fragment?
                        mainHandler.post(() ->
                                cardsInVMReady.setValue(false)
                        );
                    }
                }
            });
        }
    };

    private final Observer<List<Card>> cardObserver = new Observer<List<Card>>() {
        @Override
        public void onChanged(List<Card> cards) {
            setUpDeck(cards);
        }
    };


    //OLD IMPL (NOT IN USE)
//    private final Observer<List<Card>> observer = new Observer<List<Card>>() {
//        @Override
//        public void onChanged(List<Card> cards) {
//            setUpDeck(cards);
//        }
//    };
//    public final LiveData<List<Card>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
//        //TODO - Remove bulk of logic is here, make sure doesn't run on main thread...
//        //Set the String to identify the deck
//        setCurrentDeckSeed(inputList);
//        //Deploy use cases
//        useCaseManager.runAllUseCases(inputList, currentDeckSeed, new UseCaseCallback<Boolean>() {
//            @Override
//            public void onComplete(Result<Boolean> result) {
//                if(result instanceof Result.Success) {
//                    //do something happy - use the callback
//                    cardsReady.setValue(true);
//                } else {
//                    //show on UI that no cards could be found
//                    // send signal to waiting CardFragment to move to sep 'error' fragment?
//                }
//            }
//        });
//        Log.d(TAG, Thread.currentThread().getName() + " returning cardUseCase.getCards(deckSeed) within userAnswers Transformations.switchMap...");
//        return cardUseCase.getCards(currentDeckSeed);
//    });

    @Inject
    public SharedViewModel(GetSimilarWordsUseCase similarWordsUseCase,
                           GetJpWordsUseCase jpWordsUseCase,
                           CreateAndGetCardUseCase cardUseCase,
                           ExecutorService executorService) {

        useCaseManager = UseCaseManager
                .getInstance(similarWordsUseCase, jpWordsUseCase, cardUseCase, executorService);

//        this.similarWordsUseCase = similarWordsUseCase;
//        this.jpWordsUseCase = jpWordsUseCase;
//        this.cardUseCase = cardUseCase;

        //Observe the LiveData ie user input, passing in an observer that does the logic.
//        userAnswers.observeForever(observer);
        userInputs.observeForever(inputObserver);
        cardTransformation.observeForever(cardObserver);

    }

    //Set the deckSeed within VM so it survives config changes.
    //The 'deckSeed' is just the inputWords, separated by commas and spaces and bracketed with curly braces.
    private void setCurrentDeckSeed(List<String> inputList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < inputList.size(); i++) {
            if(i == 0) {
                stringBuilder.append("{").append(inputList.get(i)).append(", ");
            } else if (i == inputList.size() - 1) {
                stringBuilder.append(inputList.get(i)).append("}");
            } else {
                stringBuilder.append(inputList.get(i)).append(", ");
            }
        }

        currentDeckSeed = stringBuilder.toString();
    }

//    private void runUseCases(List<String> inputList, String deckSeed,
//                             UseCaseCallback<Boolean> cardsReady) {
////        for (String s: inputList) {
//            //semantic search here using DatamuseAPI
////            List<String> simWords = similarWordsUseCase.run(s);
//            //Test so we can stop needlessly calling API...
//                List<String> fakeSimWords = new ArrayList<>(List.of("to speed up", "to fail to notice", "inspection"));
//
//            //get the Jp translations
//            jpWordsUseCase.run(fakeSimWords);
//
//            //set the deckSeed for the cardUseCase
//            cardUseCase.setDeckSeed(deckSeed);
////        }
//    }

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
                currentCard = new Card("Finished deck", "Finished deck");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, Thread.currentThread().getName() + ", " + e.getMessage() +
                    "\ngetNextCard() has thrown NPE");
        }
        return currentCard;
    }

    public Boolean isRunAllUseCasesSuccessful() {
        return cardsInVMReady.getValue();
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
        useCaseManager.deleteAllCards();
        return true;
    }

    @Override
    protected void onCleared() {
//        userAnswers.removeObserver(observer);
        userInputs.removeObserver(inputObserver);
        cardTransformation.removeObserver(cardObserver);
        super.onCleared();
    }
}