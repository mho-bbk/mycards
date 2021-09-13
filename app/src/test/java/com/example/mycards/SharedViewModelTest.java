package com.example.mycards;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.example.mycards.usecases.UseCaseManager;
import com.example.mycards.utility.CurrentThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//Not testing getters and setters
public class SharedViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private SharedViewModelFactory sharedViewModelFactory;     //not used
    private SharedViewModel testSharedViewModel;

    @Mock
    public UseCaseManager useCaseManager;

    private final ExecutorService executorService = new CurrentThreadExecutor();

    @Before
    public void setUp() throws IOException {
        //instantiate VM using usecase mocks
//        testSharedViewModel = sharedViewModelFactory.create(SharedViewModel.class);   //Also tried this, also results in NPE and obscures reason
        //TODO - set up when...then... for useCaseManager.getDecks() as this is required in set up
        testSharedViewModel = new SharedViewModel(useCaseManager);
    }

    //TODO - test LiveData values are setting accordingly
    // + Methods: getDecks() - observed by DeckAdapter (check this),
    //             setUserInputs() - sets the userInput which triggers Transformation.switchMap -> observer
    // + Fields: (private) cardObserver.onChanged(List<Card>) - observes cardTransformation, which transforms when cardInRepoReady is updated (NOT just when it's ready...)
    //          cardsInRepoReady - MutableLiveData that is set when the VM receives callback from UCM saying cards are ready
    //          cardsInVMReady - MutableLiveData that is set when cardObserver finishes setting up deck in VM
    //          cardTransformation - Transformation.switchMap that 'observes' cardsInRepoReady and returns the LiveData<List<Card>> from the CardRepository so the VM can access it
    //          (private) inputObserver.onChanged(List<String) - observes the userInput that is set by Fragment in VM and passes it to the useCaseManager for processing
    //          userInputs - MutableLiveData<List<String>> is set by Fragment and represents the user's input
    //          decksVMCopy() - LiveData<List<Deck>> stores the decks that are retrieved from getDecks()
    //
    // Ref: https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
    //  (Associated github) https://gist.github.com/JoseAlcerreca/1e9ee05dcdd6a6a6fa1cbfc125559bba
    //  Need to account for how JUnit has no idea about main thread/doesn't allow for async operations

    //Also this: https://medium.com/mindorks/unit-testing-for-viewmodel-19f4d76b20d4

    //Private methods we MUST indirectly test bc critical to VM function:
    // + setUpDeckInVM(List<Card>)

    @Test
    public void testGetCurrentCard() {
        //TODO
        //What does this indirectly test?
    }

    @Test
    public void testGetNextCard() {
        //TODO
        //What does this indirectly test?
    }

}