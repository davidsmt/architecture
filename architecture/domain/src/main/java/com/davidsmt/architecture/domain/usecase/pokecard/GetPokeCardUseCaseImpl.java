package com.davidsmt.architecture.domain.usecase.pokecard;

import android.support.annotation.NonNull;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.DomainError;
import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.usecase.base.BaseUseCase;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;
import com.davidsmt.architecture.repository.RepositoryException;
import com.davidsmt.architecture.repository.pokecard.PokeCardRepository;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class GetPokeCardUseCaseImpl extends BaseUseCase<GetPokeCardUseCaseImpl.Params, PokeCard> implements GetPokeCardUseCase {

    private PokeCardRepository repository;

    public GetPokeCardUseCaseImpl(PokeCardRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public Response<PokeCard> task(Params param) {

        Response<PokeCard> response = new Response<>();

        try {
            PokeCard pokeCard = repository.getPokeCard(param.getCardId());
            if (pokeCard != null) {
                response.setData(pokeCard);
            } else {
                response.setError(new DomainError(404, "PokeCard not found"));
            }
        } catch (RepositoryException exception) {
            response.setError(new DomainError(DomainError.REPOSITORY_ERROR, exception.getMessage()));
        }

        return response;
    }

    @Override
    public <Output> void getPokeCard(String cardId, BaseMapper<PokeCard, Output> mapper, OnResultCallback<Output> callback) {
        execute(mapper, new Params(cardId), callback);
    }

    static class Params extends BaseUseCase.Params {
        private String cardId;

        Params(String cardId) {
            this.cardId = cardId;
        }

        String getCardId() {
            return cardId;
        }
    }

}
