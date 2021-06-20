package com.example.mycards.ui.main;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.mycards.R;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainViewModel extends ViewModel {

    private final Map<String, String> testDictionary =
            Map.of("apple", "りんご", "orange", "オレンジ", "watermelon", "スイカ");
    private Map<String, Boolean> shownWords = new HashMap<>();

    {
        testDictionary.entrySet().forEach(entry -> shownWords.put(entry.getKey(), false));
    }

    public Map<String, String> getTestDictionary() {
        return testDictionary;
    }

    public void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setFirstCard(Fragment flashcardDisplay) {
        TextView sideA = flashcardDisplay.getView().findViewById(R.id.side_a);
        TextView sideB = flashcardDisplay.getView().findViewById(R.id.side_b);
        String testKey = "apple";

        sideA.setText(testKey);
        sideB.setText(testDictionary.get(testKey));
    }
}