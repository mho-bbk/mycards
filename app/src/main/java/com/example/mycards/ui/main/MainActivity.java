package com.example.mycards.ui.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;
import com.example.mycards.ui.carddisplay.CardDisplayActivity;

public class MainActivity extends AppCompatActivity {

    private MainPromptViewModel mainPromptViewModel;
    private Button makeCards;
    private ActivityResultLauncher<Intent> launcher;

    private EditText jobEditTxt;
    private EditText hobbyEditTxt;
    private EditText subjectEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //This is to implement the Factory - TODO: replace with dependency injection
        AnswerRepository repository = new AnswerRepository(this.getApplication());
        MainPromptVMFactory promptFactory = new MainPromptVMFactory(repository);

        mainPromptViewModel = new ViewModelProvider(this, promptFactory).get(MainPromptViewModel.class);
        // TODO: Use the ViewModel

        makeCards = findViewById(R.id.makeCardsBtn);
        jobEditTxt = findViewById(R.id.jobEditTxt);
        hobbyEditTxt = findViewById(R.id.hobbyEditTxt);
        subjectEditTxt = findViewById(R.id.subjectEditTxt);

        //Don't think this is right - we're not waiting for anything to come back from CardDisplayActivity...
//        launcher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if(result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        //pass results to the Fragment?
//
//                    }
//                }
//        );
    }

    public void createCards(View v) {

        if (passOnData()) {
            Intent intent = new Intent(this, CardDisplayActivity.class);
            startActivity(intent);
//        launcher.launch(intent);
        } else {
            Toast.makeText(this, "Ya didn't enter an answer did ya", Toast.LENGTH_LONG).show();
        }

    }

    public boolean passOnData() {
        //TODO - pass the string entered by the user into a data object to be stored in MainVM
        UserAnswer job = new UserAnswer(jobEditTxt.getText().toString());
        UserAnswer hobby = new UserAnswer(hobbyEditTxt.getText().toString());
        UserAnswer subject = new UserAnswer(subjectEditTxt.getText().toString());

        if(job.getAnswer().trim().isEmpty() || hobby.getAnswer().trim().isEmpty() ||
                subject.getAnswer().trim().isEmpty()) {
            Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
            return false;
        }

        mainPromptViewModel.upsert(job);
        mainPromptViewModel.upsert(hobby);
        mainPromptViewModel.upsert(subject);
        return true;
    }
}