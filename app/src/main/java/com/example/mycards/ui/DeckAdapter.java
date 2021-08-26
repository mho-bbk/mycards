package com.example.mycards.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.data.entities.Deck;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//Based on codepath tutorial: https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button startDeckBtn;
        public Button deleteDeckbtn;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.deck_name);
            startDeckBtn = itemView.findViewById(R.id.start_deck_button);
            deleteDeckbtn = itemView.findViewById(R.id.delete_deck_button);
        }

    }
    //Member variable for the decks

    private List<Deck> decks;
    public DeckAdapter(List<Deck> decks) {
        this.decks = decks;
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
        ViewHolder viewHolder = new ViewHolder(deckView);
        return viewHolder;
    }

    //Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull DeckAdapter.ViewHolder holder, int position) {
        //Get data model based on position
        Deck deck = decks.get(position);

        //Set the TextView text and the Button
        TextView textView = holder.nameTextView;
        textView.setText(deck.getDeckName());

        Button startDeckBtn = holder.startDeckBtn;
        startDeckBtn.setText("START DECK");  //TODO - do we implement the button logic here?

        Button deleteDeckBtn = holder.deleteDeckbtn;
        deleteDeckBtn.setText("DELETE DECK");  //TODO - do we implement the button logic here?
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }

    //Taken from Notes YT Tutorial [LINK]
    public void setDecks(List<Deck> decks) {
        this.decks = decks;
        //TODO - who's observing? does this update the view automatically on any changes?
        notifyDataSetChanged(); //TODO - may be a better, more specific method than this
    }
}
