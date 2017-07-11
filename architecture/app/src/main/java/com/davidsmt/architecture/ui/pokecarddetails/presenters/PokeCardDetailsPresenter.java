package com.davidsmt.architecture.ui.pokecarddetails.presenters;

import android.support.annotation.NonNull;

import com.davidsmt.architecture.domain.model.DomainError;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardUseCase;
import com.davidsmt.architecture.ui.pokecarddetails.views.PokeCardDetailsContract;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

/**
 * Created by d.san.martin.torres on 7/10/17.
 */

public class PokeCardDetailsPresenter implements PokeCardDetailsContract.Presenter {

    private final PokeCardDetailsContract.View view;
    private final String cardId;
    private final GetPokeCardUseCase getPokeCardUseCase;

    public PokeCardDetailsPresenter(@NonNull PokeCardDetailsContract.View view, String cardId, GetPokeCardUseCase getPokeCardUseCase) {
        this.view = view;
        this.view.setPresenter(this);

        this.cardId = cardId;
        this.getPokeCardUseCase = getPokeCardUseCase;
    }

    @Override
    public void start() {
        getPokeCardUseCase.getPokeCard(
                cardId,
                new PokeCardViewModel.Mapper(),
                new OnResultCallback<PokeCardViewModel>() {
                    @Override
                    public void onSuccess(PokeCardViewModel data) {
                        view.showCard(data);
                    }

                    @Override
                    public void onFailure(DomainError error) {
                        view.showError();
                    }
                }
        );
    }

}
