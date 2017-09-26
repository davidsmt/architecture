package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.pokeApp.repository.PokeCardRepositoryImpl;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 */

public class PokeCardListViewModel extends ViewModel {

    final PokeCardRepositoryImpl repo;

    @Inject
    public PokeCardListViewModel(PokeCardRepositoryImpl repository) {
        repo = repository;
    }

    public LiveData<Resource<List<PokeCard>>> getCardList(boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI) {
        return repo.getPokeCards(loadFromCache, loadFromDB, loadFromAPI);
    }

    public LiveData<Resource<PokeCard>> getCard(String cardID, boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI){
        return repo.getPokeCard(cardID, loadFromCache, loadFromDB, loadFromAPI);
    }

    public void deleteAllCards(boolean deleteFromCache, boolean deleteFromDB) {
        repo.deletePokeCards(deleteFromCache, deleteFromDB, false);
    }

    public void deleteCard(String id, boolean deleteFromCache, boolean deleteFromDB) {
        LiveData<Resource<PokeCard>> deleted = getCard(id, deleteFromCache, deleteFromDB,false);

    }

    public void addCard(PokeCard nCard, boolean addToCache, boolean addToDB) {
        repo.addPokeCard(nCard, addToCache, addToDB, false);
    }
}
