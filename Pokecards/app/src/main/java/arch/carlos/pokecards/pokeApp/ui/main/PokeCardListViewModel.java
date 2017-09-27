package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.baseArch.vo.Status;
import arch.carlos.pokecards.pokeApp.repository.PokeCardRepositoryImpl;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 */

public class PokeCardListViewModel extends ViewModel {

    final PokeCardRepositoryImpl repo;
    final MediatorLiveData<Resource<List<PokeCard>>> pokeCardList;

    @Inject
    public PokeCardListViewModel(PokeCardRepositoryImpl repository) {
        repo = repository;
        pokeCardList = new MediatorLiveData<>();
        pokeCardList.setValue(Resource.loading(new ArrayList<>()));
        loadCardList(false,true,true);
    }

    public LiveData<Resource<List<PokeCard>>> getPokeCardList() {
        return pokeCardList;
    }

    public void loadCardList(boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI) {
        LiveData<Resource<List<PokeCard>>> result = repo.getPokeCards(loadFromCache, loadFromDB, loadFromAPI);
        pokeCardList.addSource(result, listResource -> {
            // Optional retry recalling repo.getPokeCards
            if (listResource != null) {
                pokeCardList.setValue(listResource);
            }
        });
    }

    public void deleteCard(PokeCard card, boolean deleteFromCache, boolean deleteFromDB) {
        pokeCardList.addSource(repo.deletePokeCard(card.getId(), deleteFromCache, deleteFromDB, false), callResponseResource -> {
            if(callResponseResource.status == Status.SUCCESS){
                pokeCardList.getValue().data.remove(card);
                pokeCardList.setValue(Resource.success(pokeCardList.getValue().data));
            }
        });

    }

//    public LiveData<Resource<PokeCard>> getCard(String cardID, boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI){
//        return repo.getPokeCard(cardID, loadFromCache, loadFromDB, loadFromAPI);
//    }

//    public void deleteAllCards(boolean deleteFromCache, boolean deleteFromDB) {
//        repo.deletePokeCards(deleteFromCache, deleteFromDB, false);
//    }


//    public void addCard(PokeCard nCard, boolean addToCache, boolean addToDB) {
//        repo.addPokeCard(nCard, addToCache, addToDB, false);
//    }


}
