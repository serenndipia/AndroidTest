package com.redbooth.comics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static int    CHARACTER_ID = 1010733;
    private final static String privateKey   = "private_key"; // replace here with correct values
    private final static String publicKey    = "public_key"; // replace here with correct values
    private MarvelService marvelService;
    private ComicAdapter  comicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.redbooth.comics.R.layout.activity_main);

        comicAdapter = new ComicAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comic_list);
        recyclerView.setAdapter(comicAdapter);

        marvelService = new Retrofit.Builder().baseUrl(getString(R.string.base_url))
                                              .addConverterFactory(GsonConverterFactory.create())
                                              .build()
                                              .create(MarvelService.class);

        updateComicList();
    }

    private void updateComicList() {
        marvelService.getComicsFromCharacter(CHARACTER_ID, generateAuthenticationMap())
                     .enqueue(new Callback<List<Comic>>() {
                         @Override
                         public void onResponse(Call<List<Comic>> callback,
                                                Response<List<Comic>> response) {
                             if (response.isSuccessful()) {
                                 comicAdapter.setComics(response.body());
                                 comicAdapter.notifyDataSetChanged();
                             } else {
                                 displayErrorMessage();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Comic>> call, Throwable t) {
                             displayErrorMessage();
                         }

                         private void displayErrorMessage() {
                             Toast.makeText(MainActivity.this,
                                            R.string.error_message,
                                            Toast.LENGTH_SHORT).show();
                         }
                     });
    }

    private Map<String, String> generateAuthenticationMap() {
        String timestamp = "ts"; // replace here with correct values
        String hash = generateHash(timestamp);

        Map<String, String> map = new HashMap<>();
        map.put("ts", timestamp);
        map.put("apikey", publicKey);
        map.put("hash", hash);

        return map;
    }

    private String generateHash(String timestamp) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = new BigInteger(1,
                                  md.digest((timestamp +
                                          privateKey +
                                          publicKey).getBytes())).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
