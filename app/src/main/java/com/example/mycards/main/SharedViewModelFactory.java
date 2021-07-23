package com.example.mycards.main;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycards.data.repositories.DefaultCardRepository;

import org.jetbrains.annotations.NotNull;

public class SharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DefaultCardRepository repository;

    public SharedViewModelFactory(DefaultCardRepository repository) {
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
