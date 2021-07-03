package com.example.mycards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycards.ui.main.MainPromptFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainPromptFrame, MainPromptFragment.newInstance())
                    .commitNow();
        }
    }
}