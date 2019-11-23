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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.grace.pokedex.adapters.PokemonAdapter;
import org.grace.pokedex.data.Pokemon;
import org.grace.pokedex.adapters.AsyncTaskHandler;
import org.grace.pokedex.utils.PokemonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskHandler, PokemonAdapter.ItemClickListener {

    PokemonAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_pokemon);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        PokemonUtils.PokemonAsyncTask pokemonAsyncTask = new PokemonUtils.PokemonAsyncTask();
        pokemonAsyncTask.handler = this;
        pokemonAsyncTask.execute();
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
    public void onTaskEnd(Object pokemons) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter(this, (List<Pokemon>) pokemons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {
        Pokemon pokemon = adapter.getPokemon(position);

        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", pokemon.getUrl());
        startActivity(intent);
    }
}