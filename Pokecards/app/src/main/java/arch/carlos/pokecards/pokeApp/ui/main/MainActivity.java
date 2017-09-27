package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.ArchActivity;
import arch.carlos.pokecards.baseArch.ArchApplication;
import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.baseArch.vo.Status;
import arch.carlos.pokecards.databinding.FragmentCardsBinding;
import arch.carlos.pokecards.pokeApp.api.PokeService;
import arch.carlos.pokecards.pokeApp.ui.main.adapter.PokeCardListAdapter;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

public class MainActivity extends ArchActivity {

    FragmentCardsBinding mBinding;
    PokeCardListViewModel mViewModel;
    PokeCardListAdapter mAdapter;


    @Inject
    DomainCache cache;

    @Inject
    PokeService service;

    @Override
    protected void initActivity() {
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_cards);
        LinearLayoutManager llm = new LinearLayoutManager(ArchApplication.getContext());
        mBinding.recyclerView.setLayoutManager(llm);

        mAdapter = new PokeCardListAdapter(this::deleteItem);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(PokeCardListViewModel.class);
        mViewModel.getPokeCardList().observe(this, this::showCards);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getPokeCardList();
    }

    private void showCards(Resource<List<PokeCard>> cards) {

        mBinding.progressBar.setVisibility(cards.status == Status.SUCCESS ? View.GONE : View.VISIBLE);

        if (cards.data != null) {
            mAdapter.differItems(cards.data);

        }
    }


    private void deleteItem(PokeCard card) {
        mViewModel.deleteCard(card, true, true);
    }

}
