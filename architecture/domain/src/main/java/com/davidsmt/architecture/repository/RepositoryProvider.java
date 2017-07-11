package com.davidsmt.architecture.repository;

import com.davidsmt.architecture.repository.pokecard.PokeCardRepository;
import com.davidsmt.architecture.repository.pokecard.PokeCardRepositoryImpl;
import com.davidsmt.architecture.repository.pokecard.datasources.api.PokeCardApiDataSource;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class RepositoryProvider {

    public static PokeCardRepository providePokeCardRepository() {
        return PokeCardRepositoryImpl.getInstance(providePokeCardApiDataSource());
    }

    private static PokeCardApiDataSource providePokeCardApiDataSource() {
        return new PokeCardApiDataSource();
    }

}
