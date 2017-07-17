package com.davidsmt.architecture.ui.pokecards.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.davidsmt.architecture.PokeApplication;
import com.davidsmt.architecture.domain.model.DomainError;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardsUseCase;

import java.util.List;

/**
 * Created by d.san.martin.torres on 7/17/17.
 */

public class CardsViewModel extends ViewModel {

    private final GetPokeCardsUseCase getPokeCardsUseCase;
    private MutableLiveData<List<PokeCardViewModel>> pokeCards;
    private MutableLiveData<DomainError> error;

    public CardsViewModel() {
        this.getPokeCardsUseCase = PokeApplication.getUseCaseProvider().providePokeCardsUseCase();
        this.pokeCards = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
    }

    public void start() {
        getPokeCardsUseCase.getPokeCards(
                new PokeCardViewModel.MapperList(),
                new OnResultCallback<List<PokeCardViewModel>>() {
                    @Override
                    public void onSuccess(List<PokeCardViewModel> data) {
                        pokeCards.setValue(data);
                    }

                    @Override
                    public void onFailure(DomainError domainError) {
                        error.setValue(domainError);
                    }
                }

        );
    }

    public MutableLiveData<List<PokeCardViewModel>> getPokeCards() {
        return pokeCards;
    }

    public MutableLiveData<DomainError> getError() {
        return error;
    }
}
