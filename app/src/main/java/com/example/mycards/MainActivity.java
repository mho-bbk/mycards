package com.example.mycards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycards.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button displayBBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flashcardDisplayFrame, MainFragment.newInstance())
                    .commitNow();
        }

        displayBBtn = findViewById(R.id.displayToggle);
        displayBBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView sideB = findViewById(R.id.side_b);

        if(sideB.getVisibility() == View.VISIBLE) {
            sideB.setVisibility(View.INVISIBLE);
        } else {
            sideB.setVisibility(View.VISIBLE);
        }
    }
}