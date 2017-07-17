package com.davidsmt.architecture.ui.pokecards.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.FragmentCardsBinding;
import com.davidsmt.architecture.ui.pokecards.adapters.PokeCardsAdapter;
import com.davidsmt.architecture.ui.pokecards.viewmodels.CardsViewModel;
import com.davidsmt.architecture.ui.pokecards.viewmodels.PokeCardsViewModel;
import com.davidsmt.architecture.ui.pokecards.viewmodels.PokeCardViewModel;

import java.util.List;

/**
 * Created by d.san.martin.torres on 17/7/17.
 */

public class CardsFragment extends LifecycleFragment {

    private FragmentCardsBinding binding;
    private CardsViewModel cardsViewModel;

    public CardsFragment() {
        // Required empty public constructor
    }

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cards, container, false);

        cardsViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);

        initViews();
        cardsViewModel.start();// Same way presenter is initiated
        observe();// Implement observers that will update the view

        return binding.getRoot();
    }

    private void initViews() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observe() {
        cardsViewModel.getPokeCards().observe(this, new Observer<List<PokeCardViewModel>>() {
            @Override
            public void onChanged(@Nullable List<PokeCardViewModel> pokeCardList) {
                setLoadingIndicator(false);
                showCards(pokeCardList);
            }
        });
    }

    private void setLoadingIndicator(boolean active) {
        if (active) {
            binding.progressBar.show();
        } else {
            binding.progressBar.hide();
        }
    }

    private void showCards(List<PokeCardViewModel> pokeCardViewModelList) {
        binding.recyclerView.setAdapter(new PokeCardsAdapter(pokeCardViewModelList, new PokeCardsAdapter.Listener() {
            @Override
            public void onPokeCardClick(int position, PokeCardViewModel viewModel) {
                ViewModelProviders.of(getActivity()).get(PokeCardsViewModel.class).onCardDetail(viewModel);
            }
        }));
    }


}
