package com.davidsmt.architecture.ui.pokecards.presenters;

import com.davidsmt.architecture.domain.model.DomainError;
import com.davidsmt.architecture.domain.usecase.base.OnResultCallback;
import com.davidsmt.architecture.domain.usecase.pokecard.GetPokeCardsUseCase;
import com.davidsmt.architecture.ui.pokecards.contracts.PokeCardsContract;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCardsPresenter implements PokeCardsContract.Presenter {

    private final PokeCardsContract.View view;
    private final GetPokeCardsUseCase getPokeCardsUseCase;

    public PokeCardsPresenter(PokeCardsContract.View view, GetPokeCardsUseCase getPokeCardsUseCase) {
        this.view = view;
        this.view.setPresenter(this);

        this.getPokeCardsUseCase = getPokeCardsUseCase;
    }

    @Override
    public void start() {
        view.setLoadingIndicator(true);
        getPokeCardsUseCase.getPokeCards(
                new PokeCardViewModel.MapperList(),
                new OnResultCallback<List<PokeCardViewModel>>() {
                    @Override
                    public void onSuccess(List<PokeCardViewModel> data) {
                        view.setLoadingIndicator(false);
                        view.showCards(data);
                    }

                    @Override
                    public void onFailure(DomainError error) {
                        view.showError();
                    }
                }

        );
    }

    @Override
    public void onPokeCardClick(int position, PokeCardViewModel viewModel) {
        view.goToPokeCardDetails(viewModel.getCardId());
    }
}