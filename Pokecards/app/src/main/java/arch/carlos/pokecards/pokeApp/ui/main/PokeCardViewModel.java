package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.pokeApp.usecases.PokeCardRepositoryImpls;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 */

public class PokeCardViewModel extends ViewModel{

    final PokeCardRepositoryImpls repo;

    @Inject
    public PokeCardViewModel(PokeCardRepositoryImpls repository){
        repo = repository;
    }

    public LiveData<Resource<List<PokeCard>>> getList() {return repo.getPokeCards(false,false);
    }
}
