package com.example.mycards.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycards.R;
import com.example.mycards.ui.main.MainPromptFragment;

public class MainActivity extends AppCompatActivity {
    private Button makeCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainPromptFrame, MainPromptFragment.newInstance())
                    .commitNow();
        }

        makeCards = findViewById(R.id.makeCardsBtn);
    }

    public void createCards(View v) {
        Intent intent = new Intent(this, CardDisplayActivity.class);
        //TODO - pass the string entered by the user into a data object to be stored in MainVM
        startActivity(intent);
    }
}