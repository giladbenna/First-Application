package com.example.myapplication.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Models.ScoreItem;
import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ScoreItem> scoreItems;

    public ScoreBoardAdapter(Context context, ArrayList<ScoreItem> scoreItems) {
        this.context = context;
        this.scoreItems = scoreItems;
    }

    @NonNull
    @Override
    public ScoreBoardAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreBoardAdapter.ItemViewHolder holder, int position) {
        ScoreItem scoreItem = getItem(position);
        holder.score.setText(String.valueOf(scoreItem.getScore()));
        holder.name.setText(scoreItem.getName());
//        ImageLoader.getInstance().loadImage(scoreItem.getImage(), holder.player_IMG_icon);

    }

    @Override
    public int getItemCount() {
        return this.scoreItems == null ? 0 : scoreItems.size();
    }

    private ScoreItem getItem(int position) {
        return this.scoreItems.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView score;
        private MaterialTextView name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ShapeableImageView player_IMG_icon = itemView.findViewById(R.id.player_IMG_icon);
            score = itemView.findViewById(R.id.score);
            name = itemView.findViewById(R.id.player_Name);
        }
    }

}