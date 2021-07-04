package com.example.mycards.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycards.data.repositories.AnswerRepository;

import org.jetbrains.annotations.NotNull;

public class MainPromptVMFactory extends ViewModelProvider.NewInstanceFactory {

    private final AnswerRepository repository;

    public MainPromptVMFactory(AnswerRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        return (T) new MainPromptViewModel(repository);
    }
}
