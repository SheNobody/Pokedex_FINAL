package org.grace.pokedex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.grace.pokedex.R;
import org.grace.pokedex.data.PokemonType;
import org.grace.pokedex.utils.PokemonUtils;
import org.grace.pokedex.utils.TypeUtils;

import java.util.List;

public class DamageRelationAdapter extends RecyclerView.Adapter<DamageRelationAdapter.ViewHolder> {

    private PokemonType pokemonType;
    private LayoutInflater mInflater;
    private Context mContext;

    public DamageRelationAdapter(Context context, PokemonType pokemonType) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.pokemonType = pokemonType;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.damage_relation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(PokemonUtils.getDrawable(mContext, pokemonType.getName())).into(holder.damageFrom);
        holder.relation.setText(TypeUtils.relationNames[position]);
        List<String> relationTypes = pokemonType.getDamageRelations().get(TypeUtils.relationNames[position]);
        holder.rvTypes.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.rvTypes.setAdapter(new RowTypesAdapter(mContext, relationTypes));
    }

    @Override
    public int getItemCount() {
        return pokemonType.getDamageRelations().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView relation;
        ImageView damageFrom;
        RecyclerView rvTypes;

        ViewHolder(View itemView) {
            super(itemView);
            relation = itemView.findViewById(R.id.relation);
            damageFrom = itemView.findViewById(R.id.damage_from);
            rvTypes = itemView.findViewById(R.id.rv_types);
        }
    }
}