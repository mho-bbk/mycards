package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.entities.JMDictEntry;

import java.util.List;


public interface JMDictRepository {

    //Use this if the instantiation of the db is higher than data layer
    public void insertAll(List<JMDictEntry> jmDictEntries);

    public void upsert(JMDictEntry dictEntry);

    public void delete(JMDictEntry dictEntry);

    //method to get the 'best' Jp match based on gloss and sense counts/order
    public LiveData<JMDictEntry> getFirstJMDictEntry(String gloss);

    //method to get all Jp matches based on gloss
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss);

    //Should never need to use this really...
    public void deleteAllJMDictEntries();
}
