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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    private ProgressBar progressBar;

    private EditText jobEditTxt;
    private EditText hobbyEditTxt;
    private EditText subjectEditTxt;

    //Creating an observer
    private final Observer<Boolean> observeCardsReadiness = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean cardsReady) {
            if(cardsReady) {
                Log.d(TAG, "Setting InputFragment progressBar to INvisible...");
                progressBar.setVisibility(View.GONE);

                assert getParentFragment() != null;
                NavDirections goToCardDisplayFragment = InputFragmentDirections.actionMainFragment2ToCardDisplayFragment2();
                NavHostFragment.findNavController(getParentFragment()).navigate(goToCardDisplayFragment);
            } else {
                Toast.makeText(getContext(), "passOnData unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    };

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

        jobEditTxt = getView().findViewById(R.id.jobEditTxt);
        hobbyEditTxt = getView().findViewById(R.id.hobbyEditTxt);
        subjectEditTxt = getView().findViewById(R.id.subjectEditTxt);

        Button makeCards = getView().findViewById(R.id.makeCardsBtn);
        makeCards.setOnClickListener(this);

        FloatingActionButton openMaintenanceButton = getView().findViewById(R.id.openMaintenance);
        openMaintenanceButton.setOnClickListener(this);

        progressBar = getView().findViewById(R.id.inputFragmentProgressBar);

        sharedViewModel.runAllUseCasesSuccessful.observe(getViewLifecycleOwner(), observeCardsReadiness);

    }


    /**
     * Passes user input as List<String> to the VM
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void passOnData() {
        String job = jobEditTxt.getText().toString();
        String hobby = hobbyEditTxt.getText().toString();
        String subject = subjectEditTxt.getText().toString();

        //Manual way to stop blank entries
        if(job.trim().isEmpty() || hobby.trim().isEmpty() || subject.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
        } else {
            List<String> allUserInput = new ArrayList<>();
            allUserInput.add(job);
            allUserInput.add(hobby);
            allUserInput.add(subject);

            sharedViewModel.setUserInputs(allUserInput);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.makeCardsBtn:
                //TODO - this doesn't display immediately. Why? Is it bc the main thread is busy before this?
                // (Is this Database Inspector playing tricks again...?)
                Log.d(TAG, "Setting InputFragment progressBar to visible...");
                progressBar.setVisibility(View.VISIBLE);

                passOnData();
                break;
            case R.id.openMaintenance:
                NavDirections goToMaintenanceFragment = InputFragmentDirections.actionMainFragment2ToMaintenance();
                NavHostFragment.findNavController(this).navigate(goToMaintenanceFragment);
                break;
            default:
                break;
        }
    }
}