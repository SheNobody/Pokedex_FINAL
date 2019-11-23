package org.grace.pokedex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.grace.pokedex.R;
import org.grace.pokedex.data.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Pokemon> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    public PokemonAdapter(Context context, List<Pokemon> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = mData.get(position);

        Glide.with(mContext).load(pokemon.getImage()).into(holder.pokemonImage);
        holder.pokemonName.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public Pokemon getPokemon(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}