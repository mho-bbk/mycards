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
        Future<JMDictEntry> jmdict = executorService.submit(
                () -> jmDictEntryDao.getFirstJMDictEntry(gloss)
        );

        JMDictEntry jmDictEntry = new JMDictEntry();
        try {
            jmDictEntry = jmdict.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //if the search was not fruitful, handles null here
        if(jmDictEntry == null) {
            return new JMDictEntry();   //prevents NPE
        } else {
            return jmDictEntry;
        }
    }

    @Override
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss) {
        //TODO - there's no handling of null here, as above? TEST null response from Repository
        return jmDictEntryDao.getAllJMDictEntries(gloss);
    }

    @Override
    public void deleteAllJMDictEntries() {
        executorService.execute(() -> jmDictEntryDao.deleteAllJMDictEntries());
    }
}
