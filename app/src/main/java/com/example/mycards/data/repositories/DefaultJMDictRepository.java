package com.example.mycards.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.data.db.JMDictEntryDao;
import com.example.mycards.data.db.JMDictEntryDatabase;
import com.example.mycards.data.entities.JMDictEntry;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class DefaultJMDictRepository implements JMDictRepository {

    private final JMDictEntryDao jmDictEntryDao;
    private final static String TAG = "DefaultJMDictRepository";
    private final ExecutorService executorService;

    @Inject
    public DefaultJMDictRepository(Application application, ExecutorService executorService) {
        JMDictEntryDatabase db = JMDictEntryDatabase.getInstance(application);
        this.jmDictEntryDao = db.getJMDictEntryDao();
        this.executorService = executorService;
    }

    //These are the methods exposed to the ViewModel
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    @Override
    public void insertAll(List<JMDictEntry> jmDictEntries) {
        executorService.execute(() -> jmDictEntryDao.insertAll(jmDictEntries));
    }

    @Override
    public void upsert(JMDictEntry dictEntry) {
        executorService.execute(() -> jmDictEntryDao.upsert(dictEntry));
    }

    @Override
    public void delete(JMDictEntry dictEntry) {
        executorService.execute(() -> jmDictEntryDao.delete(dictEntry));
    }

    @Override
    public JMDictEntry getFirstJMDictEntry(String gloss) {
        return jmDictEntryDao.getFirstJMDictEntry(gloss);
    }

    @Override
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss) {
        //TODO - does this need to return live data?
        return jmDictEntryDao.getAllJMDictEntries(gloss);
    }

    @Override
    public void deleteAllJMDictEntries() {
        executorService.execute(jmDictEntryDao::deleteAllJMDictEntries);
    }
}
