package com.example.mycards.ui.main;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycards.R;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.setFirstCard(this);
    }

    public void toggleVisibility(View view) {
        mViewModel.toggleVisibility(view);
    }

    public void moveToNextCard() {
        //Random int to access random key and set random flashcard value?
        Map<String, String> dictionary = mViewModel.getTestDictionary();
        TextView sideA = getView().findViewById(R.id.side_a);
        TextView sideB = getView().findViewById(R.id.side_b);

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            sideA.setText(entry.getKey());
            sideB.setText(entry.getValue());
        }
    }

}