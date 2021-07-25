package com.example.mycards.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycards.R;
import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.example.mycards.data.repositories.DefaultCardRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InputFragment extends Fragment implements View.OnClickListener {

    private SharedViewModel mainPromptViewModel;
    private Button makeCards;
    private FloatingActionButton openMaintenanceButton;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO - use dependency injection
        DefaultCardRepository repository = new DefaultCardRepository(getActivity().getApplication());
        SharedViewModelFactory factory = new SharedViewModelFactory(repository);

        mainPromptViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);
        try(InputStream input = getResources().openRawResource(R.raw.jmdict_eng_common_3_1_0)) {
            mainPromptViewModel.loadJMDict(input);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }

        jobEditTxt = getView().findViewById(R.id.jobEditTxt);
        hobbyEditTxt = getView().findViewById(R.id.hobbyEditTxt);
        subjectEditTxt = getView().findViewById(R.id.subjectEditTxt);

        makeCards = getView().findViewById(R.id.makeCardsBtn);
        makeCards.setOnClickListener(this);

        openMaintenanceButton = getView().findViewById(R.id.openMaintenance);
        openMaintenanceButton.setOnClickListener(this);

    }

    public void createCards() {
        if(passOnDataSuccessful()) {
            NavDirections goToCardDisplayFragment = InputFragmentDirections.actionMainFragment2ToCardDisplayFragment2();
            NavHostFragment.findNavController(this).navigate(goToCardDisplayFragment);
        } else {
            Toast.makeText(getContext(), "passOnData unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean passOnDataSuccessful() {
        //TODO - pass the string entered by the user into a data object to be stored in MainVM
        String job = jobEditTxt.getText().toString();
        String hobby = hobbyEditTxt.getText().toString();
        String subject = subjectEditTxt.getText().toString();

        //Manual way to stop blank entries
        if(job.trim().isEmpty() || hobby.trim().isEmpty() || subject.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
            return false;
        }

        List<String> allUserInput = new ArrayList<>();
        allUserInput.add(job);
        allUserInput.add(hobby);
        allUserInput.add(subject);

//        mainPromptViewModel.upsert(job);
//        mainPromptViewModel.upsert(hobby);
//        mainPromptViewModel.upsert(subject);
        mainPromptViewModel.setUserInputs(allUserInput);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.makeCardsBtn:
                createCards();
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