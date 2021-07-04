package com.example.mycards.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.ui.carddisplay.CardDisplayFragment;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button displayBBtn;
    private Button nextBtn;
    private Button repeatBtn;
    private Button backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_display_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flashcardDisplayFrame, CardDisplayFragment.newInstance())
                    .commitNow();
        }

        displayBBtn = findViewById(R.id.displayToggle);
        displayBBtn.setOnClickListener(this);
        nextBtn = findViewById(R.id.nextFlashcard);
        nextBtn.setOnClickListener(this);
        repeatBtn = findViewById(R.id.repeatFlashcard);
        repeatBtn.setOnClickListener(this);
        backToHome = findViewById(R.id.backToHome);
        backToHome.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //Retrieve flashcard display Fragment
        CardDisplayFragment flashcardDisplay = (CardDisplayFragment)
                getSupportFragmentManager().findFragmentById(R.id.flashcardDisplayFrame);

        switch(v.getId()) {
            case R.id.displayToggle:
                flashcardDisplay.toggleVisibility(findViewById(R.id.side_b));
                break;
            case R.id.nextFlashcard:
                flashcardDisplay.nextCard();
                break;
            case R.id.repeatFlashcard:
                flashcardDisplay.repeatCard();
                Toast.makeText(this,
                        flashcardDisplay.getCurrentCardAsString() + " will repeat at end of deck",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.backToHome:
                finish();   //clears activity from the stack
                break;
            default:
                break;
        }
    }
}