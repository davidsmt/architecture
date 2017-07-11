package com.davidsmt.architecture.repository.pokecard.datasources.api;

import com.davidsmt.architecture.repository.pokecard.datasources.api.models.PokeCardApi;
import com.davidsmt.architecture.repository.pokecard.datasources.api.models.PokeCardsApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public interface PokeCardService {

    @GET("cards")
    Call<PokeCardsApi> getPokeCards();

    @GET("cards/{id}")
    Call<PokeCardApi> getPokeCard(@Path("id") String id);

}
