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
import java.util.concurrent.Future;

import javax.inject.Inject;

public class DefaultJMDictRepository implements JMDictRepository {

    private JMDictEntryDao jmDictEntryDao;
    private final static String TAG = "DefaultJMDictRepository";

    @Inject
    public DefaultJMDictRepository(Application application) {
        JMDictEntryDatabase db = JMDictEntryDatabase.getInstance(application);
        jmDictEntryDao = db.getJMDictEntryDao();
    }

    //These are the methods exposed to the ViewModel
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    @Override
    public void insertAll(List<JMDictEntry> jmDictEntries) {
        JMDictEntryDatabase.jmDictDatabaseWriteExecutor.execute(() -> jmDictEntryDao.insertAll(jmDictEntries));
    }

    @Override
    public void upsert(JMDictEntry dictEntry) {
        JMDictEntryDatabase.jmDictDatabaseWriteExecutor.execute(() -> jmDictEntryDao.upsert(dictEntry));
    }

    @Override
    public void delete(JMDictEntry dictEntry) {
        JMDictEntryDatabase.jmDictDatabaseWriteExecutor.execute(() -> jmDictEntryDao.delete(dictEntry));
    }

    @Override
    public JMDictEntry getFirstJMDictEntry(String gloss) {
        Future<JMDictEntry> jmdict = JMDictEntryDatabase.jmDictDatabaseWriteExecutor.submit(
                () -> jmDictEntryDao.getFirstJMDictEntry(gloss)
        );

        JMDictEntry jmDictEntry = new JMDictEntry();
        try {
            jmDictEntry = jmdict.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if(jmDictEntry == null) {
            return new JMDictEntry();
        } else {
            return jmDictEntry;
        }
    }

    @Override
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss) {
        return jmDictEntryDao.getAllJMDictEntries(gloss);
    }

    @Override
    public void deleteAllJMDictEntries() {
        JMDictEntryDatabase.jmDictDatabaseWriteExecutor.execute(() -> jmDictEntryDao.deleteAllJMDictEntries());
    }
}
