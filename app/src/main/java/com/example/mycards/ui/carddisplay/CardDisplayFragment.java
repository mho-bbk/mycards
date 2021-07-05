package com.example.mycards.ui.carddisplay;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycards.Card;
import com.example.mycards.R;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;

import java.util.Iterator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayFragment extends Fragment {

    private CardDisplayViewModel cardDisplayViewModel;
    private TextView sideA, sideB;
    private Card current;
    private Iterator<Card> cardIterator;

    public static CardDisplayFragment newInstance() {
        return new CardDisplayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_display_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is to implement the Factory - TODO: replace with dependency injection
        AnswerRepository repository = new AnswerRepository(requireActivity().getApplication());
        CardDisplayVMFactory cardFactory = new CardDisplayVMFactory(repository);

        //Implement RecyclerView
        RecyclerView cardRecyclerView = getView().findViewById(R.id.recycleViewCard);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        cardRecyclerView.setHasFixedSize(true);

        CardDisplayAdapter cardDisplayAdapter = new CardDisplayAdapter();
        cardRecyclerView.setAdapter(cardDisplayAdapter);

        cardDisplayViewModel = new ViewModelProvider(this, cardFactory).get(CardDisplayViewModel.class);
//        cardDisplayViewModel.getAllAnswers().observe(getActivity(), new Observer<List<UserAnswer>>() {
//            @Override
//            public void onChanged(List<UserAnswer> userAnswers) {
//                cardDisplayAdapter.setUserAnswers(userAnswers);
//            }
//        });

        //other set-up code
//        sideA = getView().findViewById(R.id.side_a);
//        sideB = getView().findViewById(R.id.side_b);
//        cardIterator = cardDisplayViewModel.getCardIterator();    //reference to the iterator stored in VM
//
//        //**HANDLING if the Activity has been destroyed eg bc screen rotation**
//        //if getLastDisplayed is not null then the user has started the deck
//        if(cardDisplayViewModel.getCurrentCard() != null) {
//            //we want the last displayed flashcard back
//            sideA.setText(cardDisplayViewModel.getCurrentCard().getSideA());
//            sideB.setText(cardDisplayViewModel.getCurrentCard().getSideB());
//        } else {
//            //we want to initialise the deck
//            initialiseDeck();
//        }
    }

    public void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void initialiseDeck() {
        if(cardIterator.hasNext()) {
            current = cardIterator.next();
            if(!current.isShown()) {
                showCard(current);
            }
        }
    }

    public void nextCard() {
        if(cardIterator.hasNext()) {
            current = cardIterator.next();
            if(!current.isShown()) {
                showCard(current);
            } else {
                //TODO - placeholder, see below
                sideA.setText("Finished Deck - no repeats (bp1)");
                sideB.setText("Finished Deck - no repeats (bp1)");
                sideB.setVisibility(View.VISIBLE);
            }
        } else {
            //cardIterator has finished which means original deck has finished
            //check for cards that user has said they want to repeat
            if(!checkIfRepeatDeckIsEmpty()) {
                cardDisplayViewModel.setCardIteratorToRepeatDeck();
                cardIterator = cardDisplayViewModel.getCardIterator();
                nextCard();
            } else {
                //Assuming there is no key for "Finished"
                //TODO - this is a placeholder. Eventually when a deck finishes we want to replace
                // with finished screen and button back to homepage
                sideA.setText("Finished Deck - no repeats (bp2)");
                sideB.setText("Finished Deck - no repeats (bp2)");
                sideB.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showCard(Card card) {
        sideA.setText(card.getSideA());
        sideB.setText(card.getSideB());

        //as the card has been shown, set its flag to true
        card.setShown(true);
        //save the current card in the View Model (handle Activity destroyed)
        cardDisplayViewModel.setCurrentCard(card);
    }

    public void repeatCard() {
        //set card shown flag false
        if(current != null) {
            current.setShown(false);
        }

        //TODO - unexpected behaviour with placeholder - when go to 'Finish' state,
        // pressing repeat means 'current' card (last shown card) will reshow on screen
    }

    private boolean checkIfRepeatDeckIsEmpty() {

        //initialise repeatDeck - TODO: do I need this bit?
        // TODO - if this is needed, consider moving to separate method; or move to VM sep meth
        cardDisplayViewModel.getTestDeck().forEach(card -> {
            if(!card.isShown()) {
                cardDisplayViewModel.addToRepeatDeck(card);
            }
        });

        boolean empty = false;
        //**TEST IF REPEAT DECK IS EMPTY**
        //repeatDeck is empty if it doesn't contain any Card
        if(cardDisplayViewModel.getRepeatDeck().isEmpty()) {
            empty = true;
        } else {
            //it is also empty if all Cards in the repeatDeck have been shown
            //aka it is NOT EMPTY if there is any Card in the deck where isShown is FALSE
            for (Card c : cardDisplayViewModel.getRepeatDeck()) {
                if(!c.isShown()) {
                    empty = false;
                    break;
                } else {
                    empty = true;
                }
            }
        }

        return empty;
    }

    public String getCurrentCardAsString() {
        return current.toString();
    }
}