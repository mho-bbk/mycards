package com.example.mycards.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InputFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "InputFragment";

    @Inject
    public SharedViewModelFactory viewModelFactory;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    private EditText jobEditTxt;
    private EditText hobbyEditTxt;
    private EditText subjectEditTxt;

    public static InputFragment newInstance() {
        return new InputFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel.class);
        navController = NavHostFragment.findNavController(this);

        jobEditTxt = getView().findViewById(R.id.jobEditTxt);
        hobbyEditTxt = getView().findViewById(R.id.hobbyEditTxt);
        subjectEditTxt = getView().findViewById(R.id.subjectEditTxt);

        Button makeCards = getView().findViewById(R.id.makeCardsBtn);
        makeCards.setOnClickListener(this);

        FloatingActionButton openMaintenanceButton = getView().findViewById(R.id.openMaintenance);
        openMaintenanceButton.setOnClickListener(this);

        FloatingActionButton openDeckButton = getView().findViewById(R.id.openDeckFragment);
        openDeckButton.setOnClickListener(this);

    }


    /**
     * Passes user input as List<String> to the VM
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean passOnData() {
        String job = preprocessString(jobEditTxt.getText().toString());
        String hobby = preprocessString(hobbyEditTxt.getText().toString());
        String subject = preprocessString(subjectEditTxt.getText().toString());

        //Manual way to stop blank entries
        if(job.isEmpty() || hobby.isEmpty() || subject.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            List<String> allUserInput = new ArrayList<>();
            allUserInput.add(job);
            allUserInput.add(hobby);
            allUserInput.add(subject);

            sharedViewModel.setUserInputs(allUserInput);
            return true;
        }
    }

    //TODO - consider the place for this - better in VM?
    private String preprocessString(String s) {
        //get rid of surrounding spaces
        //decapitalise
        //remove any punctuation (in any position) - TODO refine this
        return s.trim().toLowerCase().replaceAll("\\p{P}", "");
    }

    private void goToCardDisplayFragment() {
        NavDirections goToCardDisplayFragment = InputFragmentDirections.actionMainFragment2ToCardDisplayFragment2();
        navController.navigate(goToCardDisplayFragment);
        Log.d(TAG, Thread.currentThread().getName() + " moving to CardDisplayFragment...");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.makeCardsBtn:
                if(passOnData()) {
                    goToCardDisplayFragment();
                } else {
                    //do nothing
                }
                break;
            case R.id.openMaintenance:
                NavDirections goToMaintenanceFragment = InputFragmentDirections.actionMainFragment2ToMaintenance();
                navController.navigate(goToMaintenanceFragment);
                break;
            case R.id.openDeckFragment:
                NavDirections goToDeckFragment = InputFragmentDirections.actionMainFragment2ToDeckFragment();
                navController.navigate(goToDeckFragment);
                break;
            default:
                break;
        }
    }
}