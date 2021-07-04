package com.example.mycards.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.db.AnswersDatabase;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainPromptViewModel extends ViewModel {

    private AnswerRepository answerRepository;

    public MainPromptViewModel(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    public void upsert(UserAnswer answer) {
        answerRepository.upsert(answer);
    }

    //TODO - not sure we need methods below here as this VM will only receive answers
    public void delete(UserAnswer answer) {
        answerRepository.delete(answer);
    }

    public LiveData<List<UserAnswer>> getAllAnswers() { return answerRepository.getAllAnswers(); }

    public void deleteAllAnswers() {
        answerRepository.deleteAllAnswers();
    }
}