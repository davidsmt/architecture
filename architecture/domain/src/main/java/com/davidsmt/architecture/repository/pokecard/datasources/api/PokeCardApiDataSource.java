package com.davidsmt.architecture.repository.pokecard.datasources.api;

import android.support.annotation.Nullable;

import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.model.PokeCards;
import com.davidsmt.architecture.repository.RepositoryException;
import com.davidsmt.architecture.repository.Service;
import com.davidsmt.architecture.repository.pokecard.datasources.PokeCardDataSource;
import com.davidsmt.architecture.repository.pokecard.datasources.api.models.PokeCardApi;
import com.davidsmt.architecture.repository.pokecard.datasources.api.models.PokeCardsApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCardApiDataSource implements PokeCardDataSource {

    @Nullable
    @Override
    public PokeCard getPokeCard(String id) throws RepositoryException {
        PokeCardService service = Service.with(PokeCardService.class);
        Call<PokeCardApi> call = service.getPokeCard(id);
        Response<PokeCardApi> response;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return new PokeCardApi.Mapper().map(response.body());
            }else {
                // Parse error object if it is available
                throw new RepositoryException(RepositoryException.API, "Error");
            }
        } catch (IOException e) {
            //ignored
        }
        return null;
    }

    @Override
    public List<PokeCard> getPokeCards() throws RepositoryException {
        PokeCardService service = Service.with(PokeCardService.class);
        Call<PokeCardsApi> call = service.getPokeCards();
        Response<PokeCardsApi> response;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                PokeCards pokeCards = new PokeCardsApi.Mapper().map(response.body());
                return pokeCards.getCards();
            }else {
                // Parse error object if it is available
                throw new RepositoryException(RepositoryException.API, "Error");
            }
        } catch (IOException e) {
            //ignored
        }
        return null;
    }

}
