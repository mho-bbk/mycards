package com.example.mycards.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;

import javax.inject.Inject;

//From Android Dagger Docs:
// "When using fragments, inject Dagger in the fragment's onAttach() method.
// In this case, it can be done before or after calling super.onAttach()."

public class Maintenance extends Fragment {

    private static final String TAG = "Maintenance";

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;

    public static Maintenance newInstance() {
        return new Maintenance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maintenance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);

        Button deleteAllCardsBtn = getView().findViewById(R.id.maintenanceDeleteAllCards);
        deleteAllCardsBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                sharedViewModel.deleteAllCards();
                Toast.makeText(getActivity(), "Card database has been cleared", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: Card database has been cleared");
            }
        });

        Button deleteAllDecksBtn = getView().findViewById(R.id.maintenanceDeleteAllDecks);
        deleteAllDecksBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                sharedViewModel.deleteAllDecks();
                Toast.makeText(getActivity(), "Deck database has been cleared", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: Deck database has been cleared");
            }
        });
    }
}