package com.davidsmt.architecture.domain.usecase;

import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardUseCase;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardsUseCase;

/**
 * Created by d.san.martin.torres on 7/11/17.
 */

public interface UseCaseProvider {

    GetPokeCardUseCase providePokeCardUseCase();

    GetPokeCardsUseCase providePokeCardsUseCase();

}
