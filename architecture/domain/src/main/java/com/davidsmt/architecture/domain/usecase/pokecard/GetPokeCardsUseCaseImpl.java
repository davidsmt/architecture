package com.davidsmt.architecture.domain.usecase.pokecard;

import android.support.annotation.NonNull;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.DomainError;
import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.usecase.base.BaseUseCase;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;
import com.davidsmt.architecture.repository.RepositoryException;
import com.davidsmt.architecture.repository.pokecard.PokeCardRepository;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class GetPokeCardsUseCaseImpl extends BaseUseCase<BaseUseCase.Params.VoidParams, List<PokeCard>> implements GetPokeCardsUseCase {

    private PokeCardRepository repository;

    public GetPokeCardsUseCaseImpl(PokeCardRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public Response<List<PokeCard>> task(Params.VoidParams param) {
        Response<List<PokeCard>> response = new Response<>();

        try {
            List<PokeCard> pokeCards = repository.getPokeCards();
            if (pokeCards != null) {
                response.setData(pokeCards);
            } else {
                response.setError(new DomainError(404, "PokeCard empty list"));
            }
        } catch (RepositoryException exception) {
            response.setError(new DomainError(DomainError.REPOSITORY_ERROR, exception.getMessage()));
        }

        return response;
    }

    @Override
    public <Output> void getPokeCards(BaseMapper<List<PokeCard>, Output> mapper, OnResultCallback<Output> callback) {
        execute(mapper, callback);
    }
}
