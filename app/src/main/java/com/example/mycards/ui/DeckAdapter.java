package com.example.mycards.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.data.entities.Deck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * References:
 * + Codepath Tutorial: https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 * + Coding With Mitch (YT): https://www.youtube.com/watch?v=69C1ljfDvl0&ab_channel=CodingWithMitch
 * + Coding With Mitch (GitHub): https://github.com/mitchtabian/SQLite-for-Beginners-2019/blob/master/app/src/main/java/com/codingwithmitch/notes/adapters/NotesRecyclerAdapter.java
 */
public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button startDeckBtn;
        public FloatingActionButton deleteDeckbtn;
        OnDeckClickListener deckClickListener;

        public ViewHolder(View itemView, OnDeckClickListener deckClickListener) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.deck_name);
            startDeckBtn = itemView.findViewById(R.id.start_deck_button);
            deleteDeckbtn = itemView.findViewById(R.id.delete_deck_button);
            this.deckClickListener = deckClickListener;

            startDeckBtn.setOnClickListener(this);
            deleteDeckbtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.start_deck_button:
                    Log.d(TAG,"onClick: Start deck button pushed");
                    deckClickListener.onDeckClickStart(decks.get(getBindingAdapterPosition()));
                    break;
                case R.id.delete_deck_button:
                    Log.d(TAG, "onClick: Delete deck button pushed");
                    deckClickListener.onDeckClickDelete(decks.get(getBindingAdapterPosition()));
                    break;
                default:
                    break;
            }
        }
    }

    private static final String TAG = "DeckAdapter";

    //Member variable for the decks
    private List<Deck> decks = new ArrayList<>();
    private OnDeckClickListener deckClickListener;

    public DeckAdapter(OnDeckClickListener deckClickListener) {
        this.deckClickListener = deckClickListener;
    }

    //inflate a layout from XML and return the holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflate custom layout
        View deckView = inflater.inflate(R.layout.item_deck, parent, false);

        //Return a new holder instance
        return new ViewHolder(deckView, deckClickListener);
    }

    //Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull DeckAdapter.ViewHolder holder, int position) {
        //Get data model based on position
        Deck deck = decks.get(position);

        //Set the TextView text and the Button
        TextView textView = holder.nameTextView;
        textView.setText(deck.getDeckName());

    }

    @Override
    public int getItemCount() {
        return decks.size();
    }

    //Taken from Notes YT Tutorial [LINK]
    public void setDecks(List<Deck> decks) {
        this.decks = decks;
        notifyDataSetChanged(); //TODO - may be a better, more specific method than this
    }

    //Best practice for using OnClickListener in RecyclerView item is via interface
    public interface OnDeckClickListener {
        void onDeckClickStart(Deck deck);
        void onDeckClickDelete(Deck deck);
        //could interface have a method that returns the deck in position?
    }
}
