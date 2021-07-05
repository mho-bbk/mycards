package com.example.mycards.ui.carddisplay;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.Card;
import com.example.mycards.R;
import com.example.mycards.data.entities.UserAnswer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayAdapter extends RecyclerView.Adapter<CardDisplayAdapter.CardHolder> {
//    private List<UserAnswer> userAnswers = new ArrayList<>();   //initialise so not null
    //test deck for the timebeing - real deck will be injected in when link to db
    private List<UserAnswer> userAnswers =
            List.of(new UserAnswer("apple"),
                    new UserAnswer("orange"),
                    new UserAnswer("watermelon"));

    @NonNull
    @NotNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_recycler, parent, false);
        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CardDisplayAdapter.CardHolder holder, int position) {
        UserAnswer userAnswer = userAnswers.get(position);
        holder.sideA.setText(userAnswer.getAnswer());
        holder.sideB.setText(userAnswer.getAnswer() + " in Japanese");
    }

    @Override
    public int getItemCount() { //should equal the num of prompts
        return userAnswers.size();
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
        notifyDataSetChanged();
    }

    //Inner class
    class CardHolder extends RecyclerView.ViewHolder {
        private final TextView sideA;
        private final TextView sideB;

        public CardHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sideA = itemView.findViewById(R.id.side_a);
            sideB = itemView.findViewById(R.id.side_b);
        }
    }
}
