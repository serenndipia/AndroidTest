package com.redbooth.comics;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

interface MarvelService {

    /**
     * Get all the comics from a specific character.
     */
    @GET("characters/{characterId}/comics")
    Call<List<Comic>> getComicsFromCharacter(@Path("characterId") int characterId,
                                             @QueryMap Map<String, String> digest);
}
