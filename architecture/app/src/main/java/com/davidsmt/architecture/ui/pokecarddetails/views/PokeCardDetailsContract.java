package com.davidsmt.architecture.ui.pokecarddetails.views;

import com.davidsmt.architecture.base.BasePresenter;
import com.davidsmt.architecture.base.BaseView;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

/**
 * Created by d.san.martin.torres on 7/10/17.
 */

public interface PokeCardDetailsContract {

    interface View extends BaseView<Presenter> {

        void showCard(PokeCardViewModel viewModel);

        void showError();
    }

    interface Presenter extends BasePresenter {}
}
