package com.example.mycards.ui.carddisplay;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.ui.carddisplay.CardDisplayFragment;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_display_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flashcardDisplayFrame, CardDisplayFragment.newInstance())
                    .commitNow();
        }

        //Set buttons
        Button displayBBtn = findViewById(R.id.displayToggle);
        displayBBtn.setOnClickListener(this);
        Button nextBtn = findViewById(R.id.nextFlashcard);
        nextBtn.setOnClickListener(this);
        Button repeatBtn = findViewById(R.id.repeatFlashcard);
        repeatBtn.setOnClickListener(this);
        Button backToHome = findViewById(R.id.backToHome);
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