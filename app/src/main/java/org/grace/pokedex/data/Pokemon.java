package org.grace.pokedex.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pokemon {
    @PrimaryKey()
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "image")
    private String image;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;

        this.id = url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                .replace("/", "");

        String assetId = id;

        if (id.length() == 1) {
            assetId = "00" + id;
        } else if (id.length() == 2) {
            assetId = "0" + id;
        }

        this.image = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + assetId + ".png";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }
}