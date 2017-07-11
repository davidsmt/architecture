package com.davidsmt.architecture.domain.usecase;

import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardsUseCaseImpl;
import com.davidsmt.architecture.repository.RepositoryProvider;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardUseCase;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardUseCaseImpl;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardsUseCase;

/**
 * Created by d.san.martin.torres on 7/11/17.
 */

public class UseCaseProviderImpl implements UseCaseProvider {
    @Override
    public GetPokeCardUseCase providePokeCardUseCase() {
        return new GetPokeCardUseCaseImpl(RepositoryProvider.providePokeCardRepository());
    }

    @Override
    public GetPokeCardsUseCase providePokeCardsUseCase() {
        return new GetPokeCardsUseCaseImpl(RepositoryProvider.providePokeCardRepository());
    }
}
