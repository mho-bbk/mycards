package com.example.mycards.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.data.entities.Deck;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class DeckFragment extends Fragment implements View.OnClickListener, DeckAdapter.OnDeckClickListener {

    private static final String TAG = "DeckFragment";

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;
    private NavController navController;
    private DeckAdapter deckAdapter;

    public DeckFragment() {
        // Required empty public constructor
    }

    public static DeckFragment newInstance() {
        return new DeckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deck, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView deckDisplay = getView().findViewById(R.id.deckItemsRecyclerView);
        //Set layout manager to position the items
        deckDisplay.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Create adapter
        deckAdapter = new DeckAdapter(this);
        //Attach adapter to the RecyclerView to populate
        deckDisplay.setAdapter(deckAdapter);

        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);
        sharedViewModel.getDecks().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {
                deckAdapter.setDecks(decks);
            }
        });

        FloatingActionButton backToHome = getView().findViewById(R.id.backToHomeFromDeckFragment);
        backToHome.setOnClickListener(this);

        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backToHomeFromDeckFragment:
                NavDirections backToHome = DeckFragmentDirections.actionDeckFragmentToMainFragment2();
                navController.navigate(backToHome);
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onDeckClickStart(Deck deck) {
        Log.d(TAG,"Start deck button pushed for " + deck.getDeckName());
        List<String> inputList = Deck.rebuildInputList(deck.getDeckName());
        sharedViewModel.setUserInputs(inputList);
        goToCardDisplayFragment();
    }

    private void goToCardDisplayFragment() {
        NavDirections goToCardDisplayFragment = DeckFragmentDirections.actionDeckFragmentToCardDisplayFragment2();
        navController.navigate(goToCardDisplayFragment);
        Log.d(TAG, Thread.currentThread().getName() + " moving to CardDisplayFragment...");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onDeckClickDelete(Deck deck) {
        Log.d(TAG, "Delete deck button pushed for " + deck.getDeckName());
        sharedViewModel.deleteDeck(deck);
    }
}