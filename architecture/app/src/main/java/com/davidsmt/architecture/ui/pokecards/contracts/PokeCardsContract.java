package com.davidsmt.architecture.ui.pokecards.contracts;

import com.davidsmt.architecture.base.BasePresenter;
import com.davidsmt.architecture.base.BaseView;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public interface PokeCardsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showCards(List<PokeCardViewModel> pokeCardViewModelList);

        void showError();

        void goToPokeCardDetails(String cardId);
    }

    interface Presenter extends BasePresenter {

        void onPokeCardClick(int position, PokeCardViewModel viewModel);
    }
}
