package com.example.mycards;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mycards.ui.main.MainFragment;
import com.example.mycards.ui.main.MainViewModel;

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

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        //Retrieve flashcard display Fragment
        MainFragment flashcardDisplay = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.flashcardDisplayFrame);

        switch(v.getId()) {
            case R.id.displayToggle:
                flashcardDisplay.toggleVisibility(findViewById(R.id.side_b));
                break;
//            case R.id.nextFlashcard:
//                break;
            default:
                break;
        }
    }
}