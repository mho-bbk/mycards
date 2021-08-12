package com.example.mycards.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.example.mycards.R;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.DefaultJMDictRepository;
import com.example.mycards.data.repositories.JMDictRepository;
import com.example.mycards.di.AppComponent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    ExecutorService mainActivityExecutor = Executors.newSingleThreadExecutor();

    @Inject
    SharedViewModelFactory sharedViewModelFactory;
    SharedViewModel sharedViewModel;

    @Inject
    DefaultJMDictRepository jmDictRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyCardsApplication) getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = new ViewModelProvider(this, sharedViewModelFactory).get(SharedViewModel.class);

        //Issue: do not want this to run if the database has already been populated... (eg screen config change)
        Future<?> future = mainActivityExecutor.submit(() -> jmDictRepository
                .insertAll(getPrePopulatedData(this)));
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized List<JMDictEntry> getPrePopulatedData(Context context) {
        List<JMDictEntry> dictEntries = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            dictEntries = mapper.readValue(context.getResources()
                            .openRawResource(R.raw.reverse_jmdictentries_plain_sample),
                    new TypeReference<List<JMDictEntry>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictEntries;
    }
}