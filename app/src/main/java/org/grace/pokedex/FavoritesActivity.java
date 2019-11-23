package org.grace.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.grace.pokedex.adapters.PokemonAdapter;
import org.grace.pokedex.data.AppDatabase;
import org.grace.pokedex.data.Pokemon;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener {

    PokemonAdapter adapter;
    RecyclerView recyclerView;
    AppDatabase database;

    List<Pokemon> favoritePokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_pokemon);
        database = AppDatabase.getDatabase(this);
        favoritePokemons = database.pokemonDao().getAll();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter(this, favoritePokemons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePokemons.clear();
        favoritePokemons.addAll(database.pokemonDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Pokemon selectedPokemon = adapter.getPokemon(position);
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", selectedPokemon.getUrl());
        startActivity(intent);
    }
}