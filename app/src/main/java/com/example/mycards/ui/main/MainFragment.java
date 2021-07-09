package com.example.mycards.ui.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;
import com.example.mycards.ui.carddisplay.CardDisplayActivity;
import com.example.mycards.ui.carddisplay.CardDisplayFragment;
import com.example.mycards.ui.carddisplay.CardDisplayVMFactory;
import com.example.mycards.ui.carddisplay.CardDisplayViewModel;

public class MainFragment extends Fragment implements View.OnClickListener {

    private CardDisplayViewModel mainPromptViewModel;
    private Button makeCards;

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
        AnswerRepository repository = new AnswerRepository(getActivity().getApplication());
        CardDisplayVMFactory factory = new CardDisplayVMFactory(repository);

        mainPromptViewModel = new ViewModelProvider(this, factory).get(CardDisplayViewModel.class);

//        jobEditTxt = getView().findViewById(R.id.jobEditTxt);
//        hobbyEditTxt = getView().findViewById(R.id.hobbyEditTxt);
//        subjectEditTxt = getView().findViewById(R.id.subjectEditTxt);
//
//        makeCards = getView().findViewById(R.id.makeCardsBtn);
//        makeCards.setOnClickListener(this::onClick);

        //Leftover code - This is to implement the Factory - TODO: replace with dependency injection
//        AnswerRepository repository = new AnswerRepository(this.getApplication());
//        MainPromptVMFactory promptFactory = new MainPromptVMFactory(repository);
    }

////
    public void createCards() {

//        if (passOnDataSuccessful()) {
//            Intent intent = new Intent(getActivity(), CardDisplayFragment.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Ya didn't enter an answer did ya", Toast.LENGTH_LONG).show();
//        }

    }

//    public boolean passOnDataSuccessful() {
//        //TODO - pass the string entered by the user into a data object to be stored in MainVM
//        UserAnswer job = new UserAnswer(jobEditTxt.getText().toString());
//        UserAnswer hobby = new UserAnswer(hobbyEditTxt.getText().toString());
//        UserAnswer subject = new UserAnswer(subjectEditTxt.getText().toString());
//
//        if(job.getAnswer().trim().isEmpty() || hobby.getAnswer().trim().isEmpty() ||
//                subject.getAnswer().trim().isEmpty()) {
//            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        mainPromptViewModel.upsert(job);
//        mainPromptViewModel.upsert(hobby);
//        mainPromptViewModel.upsert(subject);
//        return true;
//    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.makeCardsBtn:
                createCards();
                break;
            default:
                break;
        }
    }
}