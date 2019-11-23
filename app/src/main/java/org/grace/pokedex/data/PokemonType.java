package org.grace.pokedex.data;

import java.util.List;
import java.util.Map;

public class PokemonType {

    private String name;
    private Map<String, List<String>> damageRelations;
    private List<Pokemon> pokemons;

    public PokemonType(String name, Map<String, List<String>> damageRelations, List<Pokemon> pokemons) {
        this.name = name;
        this.damageRelations = damageRelations;
        this.pokemons = pokemons;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<String>> getDamageRelations() {
        return damageRelations;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

}