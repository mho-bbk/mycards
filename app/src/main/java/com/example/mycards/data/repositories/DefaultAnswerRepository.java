package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.AnswersDatabase;
import com.example.mycards.data.db.UserAnswerDao;
import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

public class DefaultAnswerRepository implements AnswerRepository {

    private UserAnswerDao userAnswerDao;

    public DefaultAnswerRepository(Application application) {
        AnswersDatabase db = AnswersDatabase.getInstance(application);
        userAnswerDao = db.getUserAnswerDao();
    }

    //These are the methods exposed to the ViewModel
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    public void upsert(UserAnswer answer) {
        AnswersDatabase.databaseWriteExecutor.execute( () -> {
            userAnswerDao.upsert(answer);
        });
    }

    public void delete(UserAnswer answer) {
        AnswersDatabase.databaseWriteExecutor.execute( () -> {
            userAnswerDao.delete(answer);
        });
    }

    public LiveData<List<UserAnswer>> getAllAnswers() { return userAnswerDao.getAllAnswers(); }

    public void deleteAllAnswers() {
        AnswersDatabase.databaseWriteExecutor.execute( () -> {
            userAnswerDao.deleteAllAnswers();
        });
    }
}
