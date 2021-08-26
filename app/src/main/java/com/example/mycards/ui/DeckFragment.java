package com.example.mycards.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycards.R;
import com.example.mycards.data.entities.Deck;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeckFragment extends Fragment {

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    List<Deck> decks = new ArrayList<>();   //TODO - should this be kept in VM to survive destroy?

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

        RecyclerView deckDisplay = getView().findViewById(R.id.deckItemsRecyclerView);

        //Create adapter passing in data
        DeckAdapter deckAdapter = new DeckAdapter(decks);   //decks is empty to begin with
        //Attach adapter to the RecyclerView to populate
        deckDisplay.setAdapter(deckAdapter);
        //Set layout manager to position the items
        deckDisplay.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deck, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);
        navController = NavHostFragment.findNavController(this);

        //TODO - initialise the data here bc viewModel needs to be up and running...
        //decks = ...
    }
}