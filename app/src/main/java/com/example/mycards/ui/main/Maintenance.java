package com.example.mycards.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycards.R;
import com.example.mycards.SharedViewModel;
import com.example.mycards.data.repositories.DefaultAnswerRepository;
import com.example.mycards.ui.carddisplay.SharedViewModelFactory;

public class Maintenance extends Fragment {

    private SharedViewModel viewModel;

    public static Maintenance newInstance() {
        return new Maintenance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maintenance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO - use dependency injection
        DefaultAnswerRepository repository = new DefaultAnswerRepository(getActivity().getApplication());
        SharedViewModelFactory factory = new SharedViewModelFactory(repository);

        viewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

        Button deleteAll = getView().findViewById(R.id.maintenanceDeleteAll);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                viewModel.deleteAllAnswers();
                Toast.makeText(getActivity(), "AnswerDatabase has been cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}