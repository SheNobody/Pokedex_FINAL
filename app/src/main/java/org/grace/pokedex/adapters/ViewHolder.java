package org.grace.pokedex.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.grace.pokedex.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView pokemonName;
    ImageView pokemonImage;
    PokemonAdapter.ItemClickListener itemClickListener;


    public ViewHolder(View itemView, PokemonAdapter.ItemClickListener listener) {
        super(itemView);
        pokemonName = itemView.findViewById(R.id.tv_pokemon_name);
        pokemonImage = itemView.findViewById(R.id.iv_pokemon_image);
        itemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
    }
}