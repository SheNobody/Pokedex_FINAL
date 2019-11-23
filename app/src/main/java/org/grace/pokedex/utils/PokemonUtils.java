package org.grace.pokedex.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import org.grace.pokedex.adapters.AsyncTaskHandler;
import org.grace.pokedex.data.Pokemon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PokemonUtils {
    private static final String LOG_TAG = PokemonUtils.class.getSimpleName();

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);//milliseconds
            urlConnection.setConnectTimeout(15000);//milliseconds
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static Drawable getDrawable(Context context, String drawableName) {
        Resources res = context.getResources();
        int resID = res.getIdentifier(drawableName, "drawable", context.getPackageName());
        return res.getDrawable(resID);
    }

    public static class PokemonAsyncTask extends AsyncTask<Void, Void, List<Pokemon>> {

        public AsyncTaskHandler handler;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Pokemon> doInBackground(Void... voids) {

            URL url = createUrl("https://pokeapi.co/api/v2/pokemon?offset=0&limit=151");
            // Hacemos la petición. Ésta puede tirar una exepción.
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
                return listapokemon(jsonResponse);
            } catch (IOException e) {
                Log.e("Download error", "Problem making the HTTP request.", e);
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Pokemon> pokemonList) {
            super.onPostExecute(pokemonList);
            if (handler != null) {
                handler.onTaskEnd(pokemonList);
            }
        }

        private List<Pokemon> listapokemon(String jsonStr) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                ArrayList<Pokemon> pokemonShortList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String url = jsonArray.getJSONObject(i).getString("url");
                    String name = jsonArray.getJSONObject(i).getString("name");
                    pokemonShortList.add(new Pokemon(name, url));
                }
                return pokemonShortList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}