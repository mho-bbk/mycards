package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.JMDictEntryDao;
import com.example.mycards.data.db.JMDictEntryDatabase;
import com.example.mycards.data.entities.JMDictEntry;

import java.util.List;

import javax.inject.Inject;

public class DefaultJMDictRepository implements JMDictRepository {

    private JMDictEntryDao jmDictEntryDao;

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
        JMDictEntryDatabase.databaseWriteExecutor.execute(() -> jmDictEntryDao.insertAll(jmDictEntries));
    }

    @Override
    public void upsert(JMDictEntry dictEntry) {
        JMDictEntryDatabase.databaseWriteExecutor.execute(() -> jmDictEntryDao.upsert(dictEntry));
    }

    @Override
    public void delete(JMDictEntry dictEntry) {
        JMDictEntryDatabase.databaseWriteExecutor.execute(() -> jmDictEntryDao.delete(dictEntry));
    }

    @Override
    public LiveData<JMDictEntry> getFirstJMDictEntry(String gloss) {
        return jmDictEntryDao.getFirstJMDictEntry(gloss);
    }

    @Override
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss) {
        return jmDictEntryDao.getAllJMDictEntries(gloss);
    }

    @Override
    public void deleteAllJMDictEntries() {
        JMDictEntryDatabase.databaseWriteExecutor.execute(() -> jmDictEntryDao.deleteAllJMDictEntries());
    }
}
