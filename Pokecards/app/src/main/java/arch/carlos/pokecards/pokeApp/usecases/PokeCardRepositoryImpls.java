package arch.carlos.pokecards.pokeApp.usecases;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.BaseReadFromRepository;
import arch.carlos.pokecards.baseArch.repository.RepositoryService;
import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.pokeApp.api.intermediate.PokeCardListResponse;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/15/17.
 */

public class PokeCardRepositoryImpls implements BaseReadFromRepository.LocalStorageImpl<List<PokeCard>>,BaseReadFromRepository.ExternalServiceImpl<List<PokeCard>, PokeCardListResponse> {

    public final static String errorExample = "Example error msg";
    private final RepositoryService repositoryService;

    @Inject
    PokeCardRepositoryImpls(RepositoryService repo) {
        this.repositoryService = repo;
    }

    public LiveData<Resource<List<PokeCard>>> getPokeCards(boolean fetchFromBD, boolean fetchFromAPI) {
        return new BaseReadFromRepository<>(repositoryService.getAppExecutors(), this, this).getLiveData(fetchFromAPI, fetchFromBD);
    }

    public LiveData<Resource<List<PokeCard>>> getPokeCards() {
        return new BaseReadFromRepository<>(repositoryService.getAppExecutors(), this, this).getLiveData(true,true);
    }

    @Override
    public LiveData<ApiResponse<PokeCardListResponse>> API_get() {
        return repositoryService.getService().getPokeCards();
    }

    @Override
    public List<PokeCard> API_proccesToInternal(ApiResponse<PokeCardListResponse> response) {
        return response.body.getCards();
    }

    @Override
    public void API_formatError(MutableLiveData<Resource<List<PokeCard>>> partialData, String errorMessage, int errorCode) {
        if(partialData.getValue() != null){
            partialData.setValue(Resource.error(errorExample,partialData.getValue().data));
        }else{
            partialData.setValue(Resource.error(errorExample,new ArrayList<>()));
        }
    }

    @Override
    public void BD_save(@NonNull List<PokeCard> item) {
        repositoryService.getDb().pokeCardDao().insertAll(item);
    }

    @Override
    public LiveData<List<PokeCard>> BD_get() {
        return repositoryService.getDb().pokeCardDao().getCards();
    }

    @Override
    public String CACHE_getDomainKey() {
        return PokeCard.DOMAIN_KEY;
    }

    @Override
    public boolean CACHE_hasDomainCache() {
        return repositoryService.getCache().hasCacheForDomain(CACHE_getDomainKey());
    }

    @Override
    public void CACHE_save(List<PokeCard> item) {
        repositoryService.getCache().addResource(CACHE_getDomainKey(), item);
    }

    @Override
    public LiveData<List<PokeCard>> CACHE_get() {
        return repositoryService.getCache().getResources(CACHE_getDomainKey());
    }

}