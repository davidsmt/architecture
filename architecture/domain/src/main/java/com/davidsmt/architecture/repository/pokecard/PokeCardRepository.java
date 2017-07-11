package com.davidsmt.architecture.repository.pokecard;

import android.support.annotation.Nullable;

import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.repository.RepositoryException;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public interface PokeCardRepository {
    @Nullable
    PokeCard getPokeCard(String username) throws RepositoryException;

    List<PokeCard> getPokeCards() throws RepositoryException;
}
