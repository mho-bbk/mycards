package com.example.mycards.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycards.R;
import com.example.mycards.data.db.AnswersDatabase;
import com.example.mycards.data.repositories.AnswerRepository;

import java.util.Objects;

public class MainPromptFragment extends Fragment {

    private MainPromptViewModel mViewModel;

    public static MainPromptFragment newInstance() {
        return new MainPromptFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_prompt_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is to implement the Factory - TODO: replace with dependency injection
        AnswerRepository repository = new AnswerRepository(requireActivity().getApplication());
        MainPromptVMFactory promptFactory = new MainPromptVMFactory(repository);

        mViewModel = new ViewModelProvider(this, promptFactory).get(MainPromptViewModel.class);
        // TODO: Use the ViewModel
        //Note: Coding In Flow tutorial impl observe on allNotes item but not sure we need it here,
        // as our activity is destroyed once you move to/from next screen
    }

}