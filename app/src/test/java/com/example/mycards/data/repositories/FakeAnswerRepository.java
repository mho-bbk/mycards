package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

//TODO - Will be used to test network requests
//Write this to test our ViewModel
public class FakeAnswerRepository implements AnswerRepository {

    //Simulate the db
    private final List<UserAnswer> testAnswers = List.of(new UserAnswer("mallard"),
            new UserAnswer("coot"),
            new UserAnswer("swan"));

    private final MutableLiveData<List<UserAnswer>> observableTestAnswers = new MutableLiveData<>();

    {
        observableTestAnswers.setValue(testAnswers);
    }

    //TODO - below relates to network calls
    private boolean shouldReturnNetworkError = false;
    private void setShouldReturnNetworkError(boolean bool) {
        shouldReturnNetworkError = bool;
    }

    private void refreshObservableTestAnswers() {
        observableTestAnswers.postValue(testAnswers);
    }

    @Override
    public void upsert(UserAnswer answer) {
        testAnswers.add(answer);
        refreshObservableTestAnswers();
    }

    @Override
    public void delete(UserAnswer answer) {
        testAnswers.remove(answer);
        refreshObservableTestAnswers();
    }

    @Override
    public LiveData<List<UserAnswer>> getAllAnswers() {
        return observableTestAnswers;
    }

    @Override
    public void deleteAllAnswers() {
        testAnswers.clear();
        refreshObservableTestAnswers();
    }
}
