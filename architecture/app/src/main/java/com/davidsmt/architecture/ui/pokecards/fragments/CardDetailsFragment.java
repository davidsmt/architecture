package com.davidsmt.architecture.ui.pokecards.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.FragmentCardDetailsBinding;
import com.davidsmt.architecture.ui.pokecards.viewmodels.PokeCardViewModel;

/**
 * Created by d.san.martin.torres on 17/7/17.
 */

public class CardDetailsFragment extends Fragment {

    public static final String ARG_VIEWMODEL = "viewModel";

    private FragmentCardDetailsBinding binding;
    private PokeCardViewModel pokeCardViewModel;

    public CardDetailsFragment() {
        // Required empty public constructor
    }

    public static CardDetailsFragment newInstance(Bundle bundle) {
        CardDetailsFragment fragment = new CardDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokeCardViewModel = getArguments().getParcelable(ARG_VIEWMODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_details, container, false);
        binding.setViewModel(pokeCardViewModel);

        return binding.getRoot();
    }

}
