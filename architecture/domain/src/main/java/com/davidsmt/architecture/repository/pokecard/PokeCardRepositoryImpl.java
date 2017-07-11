package com.davidsmt.architecture.repository.pokecard;

import android.support.annotation.Nullable;

import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.repository.RepositoryException;
import com.davidsmt.architecture.repository.pokecard.datasources.PokeCardDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCardRepositoryImpl implements PokeCardRepository {

    private static PokeCardRepositoryImpl sInstance;

    private PokeCardDataSource dataSource;
    private final Map<String, PokeCard> cache;

    public static PokeCardRepositoryImpl getInstance(PokeCardDataSource dataSource) {
        if (sInstance == null) {
            sInstance = new PokeCardRepositoryImpl(dataSource);
        }
        return sInstance;
    }

    private PokeCardRepositoryImpl(PokeCardDataSource dataSource) {
        this.dataSource = dataSource;
        this.cache = new HashMap<>();
    }


    @Override
    public List<PokeCard> getPokeCards() throws RepositoryException {
        List<PokeCard> cards;

        if (!cache.isEmpty()) {
            cards = new ArrayList<>(cache.values());
        } else {
            cards = dataSource.getPokeCards();
            cacheResults(cards);
        }

        return cards;
    }

    @Nullable
    @Override
    public PokeCard getPokeCard(String id) throws RepositoryException {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        PokeCard card = dataSource.getPokeCard(id);
        if (card != null) {
            cache.put(id, card);
        }
        return card;
    }

    private void cacheResults(List<PokeCard> cards) {
        if (cards != null) {
            for (PokeCard card : cards) {
                cache.put(card.getId(), card);
            }
        }
    }

}
