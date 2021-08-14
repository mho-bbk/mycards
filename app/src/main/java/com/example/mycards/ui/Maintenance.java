package com.example.mycards.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import com.example.mycards.main.MyCardsApplication;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.example.mycards.data.repositories.DefaultCardRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

//From Android Dagger Docs:
// "When using fragments, inject Dagger in the fragment's onAttach() method.
// In this case, it can be done before or after calling super.onAttach()."

public class Maintenance extends Fragment {

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

        Button deleteAll = getView().findViewById(R.id.maintenanceDeleteAll);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                sharedViewModel.deleteAllCards();
                Toast.makeText(getActivity(), "Database has been cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}