package com.example.mycards.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.data.entities.Card;
import com.example.mycards.main.SharedViewModelFactory;
import com.example.mycards.data.repositories.DefaultCardRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

public class CardDisplayFragment extends Fragment implements View.OnClickListener {

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;

    private TextView sideA, sideB;

    public static CardDisplayFragment newInstance() {
        return new CardDisplayFragment();
    }

//    @Override
//    public void onAttach(@NonNull @NotNull Context context) {
//        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);
//        super.onAttach(context);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_display_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);

        // Create the observer which updates the UI.
        final Observer<List<Card>> observer = new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                startDeck(cards);
            }
        };

        // Observe the LiveData, passing in this fragment/activity as the LifecycleOwner and the observer.
        sharedViewModel.userAnswers.observe(getViewLifecycleOwner(), observer);

        //other set-up code
        sideA = getView().findViewById(R.id.side_a);
        sideB = getView().findViewById(R.id.side_b);

        //Set buttons
        Button displayBBtn = getView().findViewById(R.id.displayToggle);
        displayBBtn.setOnClickListener(this);
        Button nextBtn = getView().findViewById(R.id.nextFlashcard);
        nextBtn.setOnClickListener(this);
//        Button repeatBtn = getView().findViewById(R.id.repeatFlashcard);
//        repeatBtn.setOnClickListener(this);
//        Button backToHome = getView().findViewById(R.id.backToHome);
//        backToHome.setOnClickListener(this);
//
    }

    public void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void startDeck(List<Card> cards) {
        //TODO - rename these...
        showCard(sharedViewModel.getCurrentCard());
    }


    public void goToNextCard() {
        showCard(sharedViewModel.getNextCard());
    }

    private void showCard(Card card) {
        sideA.setText(card.getSideA());
        sideB.setText(card.getSideB());

        //By default, sideB should not be visible
        if(sideB.getVisibility() == View.VISIBLE) {
            toggleVisibility(sideB);
        }

        //as the card has been shown, set its flag to true
        card.setRepeat(true);
    }

//    public void repeatCard() {
//        //set card shown flag false
//        if(current != null) {
//            current.setShown(false);
//        }
//
//        //TODO - unexpected behaviour with placeholder - when go to 'Finish' state,
//        // pressing repeat means 'current' card (last shown card) will reshow on screen
//    }

//    private boolean checkIfRepeatDeckIsEmpty() {
//
//        //initialise repeatDeck - TODO: do I need this bit?
//        // TODO - if this is needed, consider moving to separate method; or move to VM sep meth
//        cardDisplayViewModel.getTestDeck().forEach(card -> {
//            if(!card.isShown()) {
//                cardDisplayViewModel.addToRepeatDeck(card);
//            }
//        });
//
//        boolean empty = false;
//        //**TEST IF REPEAT DECK IS EMPTY**
//        //repeatDeck is empty if it doesn't contain any Card
//        if(cardDisplayViewModel.getRepeatDeck().isEmpty()) {
//            empty = true;
//        } else {
//            //it is also empty if all Cards in the repeatDeck have been shown
//            //aka it is NOT EMPTY if there is any Card in the deck where isShown is FALSE
//            for (Card c : cardDisplayViewModel.getRepeatDeck()) {
//                if(!c.isShown()) {
//                    empty = false;
//                    break;
//                } else {
//                    empty = true;
//                }
//            }
//        }
//
//        return empty;
//    }

//    public String getCurrentCardAsString() {
//        return current.toString();
//    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.displayToggle:
                toggleVisibility(sideB);
                break;
            case R.id.nextFlashcard:
                goToNextCard();
                break;
//            case R.id.repeatFlashcard:
//                repeatCard();
//                Toast.makeText(getActivity(),
//                        getCurrentCardAsString() + " will repeat at end of deck",
//                        Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.backToHome:
////                finish();   //clears activity from the stack
//                break;
            default:
                break;
        }
    }
}