package com.example.mycards.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.mycards.R;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.DefaultJMDictRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ExecutorService activityExecutors;

    private static final String TAG = "MainActivity";

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
    }
    
    @Override
    public void onDestroy() {
        activityExecutors.shutdown();   //close thread pool
        super.onDestroy();
    }
}