package com.davidsmt.architecture.domain.usecase.pokecard;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;

import java.util.List;

/**
 * Created by d.san.martin.torres on 7/11/17.
 */

public interface GetPokeCardsUseCase {
    <Output> void getPokeCards(BaseMapper<List<PokeCard>, Output> mapper, OnResultCallback<Output> callback);
}
