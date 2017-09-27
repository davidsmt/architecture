package arch.carlos.pokecards.pokeApp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryCreate;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryDelete;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryRead;
import arch.carlos.pokecards.baseArch.repository.CallResponse;
import arch.carlos.pokecards.baseArch.repository.RepositoryService;
import arch.carlos.pokecards.baseArch.repository.notImplemented.CacheNotImplemented;
import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.pokeApp.api.apimodel.PokeCardListResponse;
import arch.carlos.pokecards.pokeApp.api.apimodel.PokeCardResponse;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/15/17.
 *
 * This repository implements each base operation by creating a base CRUD operation, telling the requested type, and then implementing the mandatory operations of their interfaces
 */

public class PokeCardRepositoryImpl {

    public final static String errorExample = "Example error msg";
    private final RepositoryService service;
    @Inject
    PokeCardRepositoryImpl(RepositoryService repo) {
        this.service = repo;
    }


    public LiveData<Resource<List<PokeCard>>> getPokeCards(boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI) {
        return new BaseRepositoryRead<>(service.getAppExecutors(), new BaseRepositoryRead.CacheReadImpl<List<PokeCard>>() {

            @Override
            public boolean CACHE_hasDomainCache() {
                return service.getCache().hasCacheForDomain(PokeCard.DOMAIN_KEY);
            }

            @Override
            public LiveData<List<PokeCard>> CACHE_get() {
                return service.getCache().getResource(PokeCard.DOMAIN_KEY);
            }

            @Override
            public void CACHE_save(List<PokeCard> item) {
                service.getCache().addResource(PokeCard.DOMAIN_KEY, item);
            }


        }, new BaseRepositoryRead.DBReadImpl<List<PokeCard>>() {
            @Override
            public LiveData<List<PokeCard>> BD_get() {
                return service.getDb().pokeCardDao().getCards();
            }

            @Override
            public void BD_save(@NonNull List<PokeCard> item) {
                service.getDb().pokeCardDao().insertAll(item);
            }


        }, new BaseRepositoryRead.ExternalServiceReadImpl<List<PokeCard>, PokeCardListResponse>() {
            @Override
            public LiveData<ApiResponse<PokeCardListResponse>> API_get() {
                return service.getService().getPokeCards();
            }

            @Override
            public List<PokeCard> API_proccesToInternal(ApiResponse<PokeCardListResponse> response) {
                return response.body.getCards();
            }

            @Override
            public void API_formatError(MutableLiveData<Resource<List<PokeCard>>> partialData, String errorMessage, int errorCode) {
                if (partialData.getValue() != null) {
                    partialData.setValue(Resource.error(errorExample, partialData.getValue().data));
                } else {
                    partialData.setValue(Resource.error(errorExample, new ArrayList<>()));
                }
            }

        }).getFrom(loadFromCache, loadFromDB, loadFromAPI);
    }

    public LiveData<Resource<PokeCard>> getPokeCard(final String id, boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI) {
        return new BaseRepositoryRead<>(service.getAppExecutors(), new BaseRepositoryRead.CacheReadImpl<PokeCard>() {
            @Override
            public boolean CACHE_hasDomainCache() {
                return service.getCache().getDomainCache(PokeCard.DOMAIN_KEY).containsKey(id);
            }

            @Override
            public LiveData<PokeCard> CACHE_get() {
                return service.getCache().getResource(PokeCard.DOMAIN_KEY, id);
            }

            @Override
            public void CACHE_save(PokeCard item) {
                service.getCache().addResource(item);
            }
        }, new BaseRepositoryRead.DBReadImpl<PokeCard>() {
            @Override
            public LiveData<PokeCard> BD_get() {
                return service.getDb().pokeCardDao().getCard(id);
            }

            @Override
            public void BD_save(@NonNull PokeCard item) {
                service.getDb().pokeCardDao().insert(item);
            }
        }, new BaseRepositoryRead.ExternalServiceReadImpl<PokeCard, PokeCardResponse>() {
            @Override
            public LiveData<ApiResponse<PokeCardResponse>> API_get() {
                return service.getService().getPokeCard(id);
            }

            @Override
            public PokeCard API_proccesToInternal(ApiResponse<PokeCardResponse> response) {
                return response.body.getCard();
            }

            @Override
            public void API_formatError(MutableLiveData<Resource<PokeCard>> partialData, String errorMessage, int errorCode) {
                if (partialData.getValue() != null) {
                    partialData.setValue(Resource.error(errorExample, partialData.getValue().data));
                } else {
                    partialData.setValue(Resource.error(errorExample, null));
                }
            }
        }).getFrom(loadFromCache, loadFromDB, loadFromAPI);
    }

    public LiveData<Resource<CallResponse>> deletePokeCard(String id, boolean deleteCache, boolean deleteDB, boolean deleteAPI) {
        return new BaseRepositoryDelete<CallResponse>(service.getAppExecutors(), new BaseRepositoryDelete.CacheDeleteImpl() {
            @Override
            public boolean CACHE_hasDomainCache() {
                return service.getCache().getDomainCache(PokeCard.DOMAIN_KEY).containsKey(id);
            }

            @Override
            public void CACHE_delete() {
                service.getCache().deleteResource(PokeCard.DOMAIN_KEY, id);
            }
        }, new BaseRepositoryDelete.DBDeleteImpl() {
            @Override
            public void BD_delete() {
                service.getDb().pokeCardDao().deleteCard(id);
            }
        }).deleteFromAllSources();

    }

    public LiveData<Resource<CallResponse>> addPokeCard(PokeCard nCard, boolean addToCache, boolean addToDB, boolean addToAPI) {
        return new BaseRepositoryCreate<CallResponse>(service.getAppExecutors(), new BaseRepositoryCreate.CacheCreateImpl() {
            @Override
            public void CACHE_create() {
                service.getCache().addResource(nCard);
            }
        }, new BaseRepositoryCreate.DBCreateImpl() {
            @Override
            public void DB_create() {
                service.getDb().pokeCardDao().insert(nCard);
            }
        }).createToAllSources();

    }

}