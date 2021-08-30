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
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoResultFragment extends Fragment {

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;

    private NavController navController;

    public NoResultFragment() {
        // Required empty public constructor
    }

    public static NoResultFragment newInstance() {
        return new NoResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);
        navController = NavHostFragment.findNavController(this);

        Button goToHome = getView().findViewById(R.id.backToHomeNoResults);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                NavDirections goToHome = NoResultFragmentDirections.actionNoResultFragment2ToMainFragment2();
                navController.navigate(goToHome);
            }
        });

        //TODO - button for decks
    }
}