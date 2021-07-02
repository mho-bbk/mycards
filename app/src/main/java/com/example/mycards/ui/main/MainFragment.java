package com.example.mycards.ui.main;

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
import android.widget.TextView;

import com.example.mycards.Card;
import com.example.mycards.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainFragment extends Fragment {
    //TODO - logic for repeating a card, linking to repeat button

    private MainViewModel mViewModel;
    private TextView sideA, sideB;
    private Card current;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //other set-up code
        sideA = getView().findViewById(R.id.side_a);
        sideB = getView().findViewById(R.id.side_b);

        //**HANDLING if the Activity has been destroyed eg bc screen rotation**
        //if getLastDisplayed is not null then the user has started the deck
        if(mViewModel.getCurrentCard() != null) {
            //we want the last displayed flashcard back
            sideA.setText(mViewModel.getCurrentCard().getSideA());
            sideB.setText(mViewModel.getCurrentCard().getSideB());
        } else {
            //we want to initialise the deck
            initialiseDeck(mViewModel.getCardIterator(), mViewModel.getTestDeck());
        }
    }

    public void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void initialiseDeck(Iterator<Card> cardIterator, List<Card> dictionary) {
        //in case cardIterator is null for whatever reason...
        if (cardIterator.hasNext()) {
            current = cardIterator.next();
            sideA.setText(current.getSideA());
            sideB.setText(current.getSideB());

            //as the card has been shown, set its flag to true
            current.setShown(true);
            //then setLastDisplayed card to the card that has just been displayed
            mViewModel.setCurrentCard(current);
        }
    }

    public void nextCard() {
        goToNextCard(mViewModel.getCardIterator(), mViewModel.getTestDeck());
    }

    private void goToNextCard(Iterator<Card> cardIterator, List<Card> dictionary) {
        if(cardIterator.hasNext()) {
            current = cardIterator.next();
            //if the word hasn't already been shown before
            //current assumption assumes no nullpointerexception
            if(!current.isShown()) {
                sideA.setText(current.getSideA());
                sideB.setText(current.getSideB());

                //as the entry equiv to testKey has been shown, set its flag to true
                current.setShown(true);
                mViewModel.setCurrentCard(current);
            }
        } else {
            //Assuming there is no key for "Finished"
            //TODO - this is a placeholder. Eventually when a deck finishes we want to replace
            // with finished screen and button back to homepage
            sideA.setText("Finished Deck");
            sideB.setText("Finished Deck");
            sideB.setVisibility(View.VISIBLE);
        }
    }

    public void repeatCard() {
        //set card in shownwords to flag false
        if(current != null) {
            current.setShown(false);
        }
        //ensure that iterator goes back around the list - don't have iterator?
    }
}