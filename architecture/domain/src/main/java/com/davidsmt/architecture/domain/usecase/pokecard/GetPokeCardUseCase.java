package com.davidsmt.architecture.domain.usecase.pokecard;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;

/**
 * Created by d.san.martin.torres on 7/11/17.
 */

public interface GetPokeCardUseCase {
    <Output> void getPokeCard(String cardId, BaseMapper<PokeCard, Output> mapper, OnResultCallback<Output> callback);
}
