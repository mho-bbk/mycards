package com.example.mycards.ui.carddisplay;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycards.R;
import com.example.mycards.SharedViewModel;
import com.example.mycards.data.repositories.AnswerRepository;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayFragment extends Fragment implements View.OnClickListener {

    private SharedViewModel cardDisplayViewModel;
//    private TextView sideA, sideB;
//    private Card current;
//    private Iterator<Card> cardIterator;

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

        //TODO - use dependency injection
        AnswerRepository repository = new AnswerRepository(getActivity().getApplication());
        CardDisplayVMFactory factory = new CardDisplayVMFactory(repository);

        cardDisplayViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

//        //other set-up code
//        sideA = getView().findViewById(R.id.side_a);
//        sideB = getView().findViewById(R.id.side_b);
//        cardIterator = cardDisplayViewModel.getCardIterator();    //reference to the iterator stored in VM
//
//        //Set buttons
//        Button displayBBtn = getView().findViewById(R.id.displayToggle);
//        displayBBtn.setOnClickListener(this);
//        Button nextBtn = getView().findViewById(R.id.nextFlashcard);
//        nextBtn.setOnClickListener(this);
//        Button repeatBtn = getView().findViewById(R.id.repeatFlashcard);
//        repeatBtn.setOnClickListener(this);
//        Button backToHome = getView().findViewById(R.id.backToHome);
//        backToHome.setOnClickListener(this);
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

//    private void initialiseDeck() {
//        if(cardIterator.hasNext()) {
//            current = cardIterator.next();
//            if(!current.isShown()) {
//                showCard(current);
//            }
//        }
//    }

//    public void nextCard() {
//        if(cardIterator.hasNext()) {
//            current = cardIterator.next();
//            if(!current.isShown()) {
//                showCard(current);
//            } else {
//                //TODO - placeholder, see below
//                sideA.setText("Finished Deck - no repeats (bp1)");
//                sideB.setText("Finished Deck - no repeats (bp1)");
//                sideB.setVisibility(View.VISIBLE);
//            }
//        } else {
//            //cardIterator has finished which means original deck has finished
//            //check for cards that user has said they want to repeat
//            if(!checkIfRepeatDeckIsEmpty()) {
//                cardDisplayViewModel.setCardIteratorToRepeatDeck();
//                cardIterator = cardDisplayViewModel.getCardIterator();
//                nextCard();
//            } else {
//                //Assuming there is no key for "Finished"
//                //TODO - this is a placeholder. Eventually when a deck finishes we want to replace
//                // with finished screen and button back to homepage
//                sideA.setText("Finished Deck - no repeats (bp2)");
//                sideB.setText("Finished Deck - no repeats (bp2)");
//                sideB.setVisibility(View.VISIBLE);
//            }
//        }
//    }

//    private void showCard(Card card) {
//        sideA.setText(card.getSideA());
//        sideB.setText(card.getSideB());
//
//        //as the card has been shown, set its flag to true
//        card.setShown(true);
//        //save the current card in the View Model (handle Activity destroyed)
//        cardDisplayViewModel.setCurrentCard(card);
//    }

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
//        switch(v.getId()) {
//            case R.id.displayToggle:
//                toggleVisibility(sideB);
//                break;
//            case R.id.nextFlashcard:
//                nextCard();
//                break;
//            case R.id.repeatFlashcard:
//                repeatCard();
//                Toast.makeText(getActivity(),
//                        getCurrentCardAsString() + " will repeat at end of deck",
//                        Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.backToHome:
////                finish();   //clears activity from the stack
//                break;
//            default:
//                break;
//        }
    }
}