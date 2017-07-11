package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.PokeCards;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCardsApi {

    @SerializedName("cards")
    private List<PokeCardApi> cards;

    public List<PokeCardApi> getCards() {
        return cards;
    }

    public static class Mapper extends BaseMapperAdapter<PokeCardsApi,PokeCards> {

        @Override
        public PokeCards map(PokeCardsApi pokeCardsApi) {
            return new PokeCards(
                    new PokeCardApi.Mapper().map(pokeCardsApi.getCards())
            );
        }

    }

}
