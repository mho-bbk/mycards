package com.example.mycards.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.DefaultAnswerRepository;
import com.example.mycards.ui.carddisplay.SharedViewModelFactory;
import com.example.mycards.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment implements View.OnClickListener {

    private SharedViewModel mainPromptViewModel;
    private Button makeCards;
    private FloatingActionButton openMaintenanceButton;

    private EditText jobEditTxt;
    private EditText hobbyEditTxt;
    private EditText subjectEditTxt;

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

        //TODO - use dependency injection
        DefaultAnswerRepository repository = new DefaultAnswerRepository(getActivity().getApplication());
        SharedViewModelFactory factory = new SharedViewModelFactory(repository);

        mainPromptViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

        jobEditTxt = getView().findViewById(R.id.jobEditTxt);
        hobbyEditTxt = getView().findViewById(R.id.hobbyEditTxt);
        subjectEditTxt = getView().findViewById(R.id.subjectEditTxt);

        makeCards = getView().findViewById(R.id.makeCardsBtn);
        makeCards.setOnClickListener(this);

        openMaintenanceButton = getView().findViewById(R.id.openMaintenance);
        openMaintenanceButton.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createCards() {
        if(passOnDataSuccessful()) {
            NavDirections goToCardDisplayFragment = MainFragmentDirections.actionMainFragment2ToCardDisplayFragment2();
            NavHostFragment.findNavController(this).navigate(goToCardDisplayFragment);
        } else {
            Toast.makeText(getContext(), "passOnData unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public boolean passOnDataSuccessful() {
        //TODO - pass the string entered by the user into a data object to be stored in MainVM
        UserAnswer job = new UserAnswer(jobEditTxt.getText().toString());
        UserAnswer hobby = new UserAnswer(hobbyEditTxt.getText().toString());
        UserAnswer subject = new UserAnswer(subjectEditTxt.getText().toString());
//
//        if(job.getAnswer().trim().isEmpty() || hobby.getAnswer().trim().isEmpty() ||
//                subject.getAnswer().trim().isEmpty()) {
//            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        mainPromptViewModel.upsert(job);
        mainPromptViewModel.upsert(hobby);
        mainPromptViewModel.upsert(subject);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.makeCardsBtn:
                createCards();
                break;
            case R.id.openMaintenance:
                NavDirections goToMaintenanceFragment = MainFragmentDirections.actionMainFragment2ToMaintenance();
                NavHostFragment.findNavController(this).navigate(goToMaintenanceFragment);
                break;
            default:
                break;
        }
    }
}