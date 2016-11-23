package com.redbooth.comics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

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

// Class created by someone learning chinese? I'm not sure
// Updated by Unknown
//
// WARNING!!! Be careful about building the Retrofit Object. It requires to be executed in the
// same order it has been developed. Otherwise, weird things can happen or the app can crash

/**
 * 耶穌巴列斯特羅
 * <p>
 * 這是應用程序的主要活動。它顯示在主屏幕和AppCompatActivity繼承
 */
public class MainActivity extends AppCompatActivity {
    private final static int    CHARACTER_ID = 1010733;
    private final static String privateKey   = "private_key"; // replace here with correct values
    private final static String publicKey    = "public_key"; // replace here with correct values
    private Server       server;
    private ComicAdapter comicAdapter;

    /**
     * 在拉曼恰，名字我不記得了，時間不長，因為住在離那些槍和盾古代，精益黑客和竊喜靈獅的貴族村
     *
     * @param savedInstanceState 堂吉訶德
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.redbooth.comics.R.layout.activity_main);
        
        comicAdapter = new ComicAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comic_list);
        recyclerView.setAdapter(comicAdapter);

        server = new Retrofit.Builder().baseUrl("http://gateway.marvel.com/v1/public/")
                                       .addConverterFactory(GsonConverterFactory.create())
                                       .build()
                                       .create(Server.class);

        updateComicList();
    }

    private void updateComicList() {
        server.getComicsFromCharacter(CHARACTER_ID, generateAuthenticationMap())
              .enqueue(new Callback<List<Comic>>() {
                  @Override
                  public void onResponse(Call<List<Comic>> callback,
                                         Response<List<Comic>> response) {
                      if (response.isSuccessful()) {
                          comicAdapter.setComics(response.body());
                          comicAdapter.notifyDataSetChanged();
                      }
                  }

                  @Override
                  public void onFailure(Call<List<Comic>> call, Throwable t) {
                      //TODO do something here
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
