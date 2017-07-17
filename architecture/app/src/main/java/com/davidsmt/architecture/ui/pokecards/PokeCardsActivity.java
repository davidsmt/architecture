package com.davidsmt.architecture.ui.pokecards;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.ActivityPokecardsBinding;
import com.davidsmt.architecture.ui.pokecards.fragments.CardDetailsFragment;
import com.davidsmt.architecture.ui.pokecards.fragments.CardsFragment;
import com.davidsmt.architecture.ui.pokecards.viewmodels.PokeCardsViewModel;

/**
 * Created by d.san.martin.torres on 17/7/17.
 */

public class PokeCardsActivity extends LifecycleActivity {

    private static final String TAG = "PokeCardsActivity";
    private static final String CARDS_FRAGMENT_TAG = "Cards";
    private static final String CARD_DETAILS_FRAGMENT_TAG = "CardDetails";

    private ActivityPokecardsBinding binding;
    private PokeCardsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokecards);

        viewModel = ViewModelProviders.of(this).get(PokeCardsViewModel.class);
        observe();
    }

    private void observe() {
        viewModel.getTransition().observe(this, new Observer<PokeCardsViewModel.Transition>() {
            @Override
            public void onChanged(@Nullable PokeCardsViewModel.Transition transition) {
                switch (transition.getState()) {
                    case Cards:
                        showCardsFragment();
                        break;
                    case CardDetail:
                        showCardDetailFragment(transition.getBundle());
                        break;
                }
            }
        });
    }

    private void showCardsFragment() {
        CardsFragment fragment = CardsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment, CARDS_FRAGMENT_TAG).commit();
    }

    private void showCardDetailFragment(Bundle bundle) {
        CardDetailsFragment fragment = CardDetailsFragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment, CARD_DETAILS_FRAGMENT_TAG).commit();
    }

    @Override
    public void onBackPressed() {
        switch(viewModel.getTransition().getValue().getState()) {
            case Cards:
                super.onBackPressed();
                break;
            case CardDetail:
                showCardsFragment();
                break;
        }
    }

}
