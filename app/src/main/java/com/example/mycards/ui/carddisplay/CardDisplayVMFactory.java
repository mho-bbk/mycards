package com.example.mycards.ui.carddisplay;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycards.SharedViewModel;
import com.example.mycards.data.repositories.AnswerRepository;

import org.jetbrains.annotations.NotNull;

public class CardDisplayVMFactory extends ViewModelProvider.NewInstanceFactory {

    private final AnswerRepository repository;

    public CardDisplayVMFactory(AnswerRepository repository) {
        this.repository = repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        return (T) new SharedViewModel(repository);
    }
}
