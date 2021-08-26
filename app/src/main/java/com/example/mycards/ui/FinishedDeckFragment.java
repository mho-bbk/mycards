package com.example.mycards.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;

import javax.inject.Inject;


public class FinishedDeckFragment extends Fragment {

    private final String TAG = "FinishedDeckFragment";

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    public FinishedDeckFragment() {
        // Required empty public constructor
    }


    public static FinishedDeckFragment newInstance() {
        return new FinishedDeckFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished_deck, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);

        Button goToHome = getView().findViewById(R.id.backToHomeFinished);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                NavDirections goHomeToInputFragment = FinishedDeckFragmentDirections.actionFinishedDeckFragmentToMainFragment2();
                navController.navigate(goHomeToInputFragment);
                Log.d(TAG, Thread.currentThread().getName() + " moving Home to InputFragment...");
            }
        });
    }
}