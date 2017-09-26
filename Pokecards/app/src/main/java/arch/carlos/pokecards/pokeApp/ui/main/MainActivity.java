package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.ArchActivity;
import arch.carlos.pokecards.baseArch.ArchApplication;
import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.baseArch.vo.Status;
import arch.carlos.pokecards.databinding.FragmentCardsBinding;
import arch.carlos.pokecards.pokeApp.ui.main.adapter.PokeCardListAdapter;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;
import arch.carlos.pokecards.pokeApp.vo.components.Ability;

public class MainActivity extends ArchActivity {

    FragmentCardsBinding mBinding;
    PokeCardListViewModel mViewModel;
    PokeCardListAdapter mAdapter;

    @Override
    protected void initActivity() {

    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_cards);
        LinearLayoutManager llm = new LinearLayoutManager(ArchApplication.getContext());
        mBinding.recyclerView.setLayoutManager(llm);
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(PokeCardListViewModel.class);

        mViewModel.getCardList(false, true, true).observe(this, listResource -> {
            if (listResource != null) {
                showCards(listResource);

            }
        });
    }

    private void showCards(Resource<List<PokeCard>> cards) {
        if (cards.status == Status.LOADING && cards.data == null) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
        }
        if (cards.status == Status.SUCCESS || cards.status == Status.LOADING && cards.data != null) {
            if (mAdapter == null) {
                mAdapter = new PokeCardListAdapter(cards.data, (id) -> {
                    deleteItem(id);
                });
                mBinding.recyclerView.setAdapter(mAdapter);
                addFakePokemon();
            } else {
                mAdapter.updateItems(cards.data);
            }

        }
        if (cards.status == Status.SUCCESS) {
            mBinding.progressBar.setVisibility(View.GONE);
        }
    }


    private void deleteItem(String id) {
        mViewModel.deleteCard(id, true, true);
        mViewModel.getCardList(true, false, false).observe(this, listResource -> {
            if (listResource.status == Status.SUCCESS) {
                mAdapter.updateItems(listResource.data);

            }
        });
    }

    private void addFakePokemon() {
//        mViewModel.addCard(fakeCard(), true, true);
//        mViewModel.getCardList(true, false, false).observe(this, listResource -> {
//            if (listResource.status == Status.SUCCESS) {
//                mAdapter.updateItems(listResource.data);
//            }
//        });
    }

//    private PokeCard fakeCard() {
//        ArrayList<String> retreat = new ArrayList<>();
//        retreat.add("fake");
//        PokeCard nCard = new PokeCard("FAKE", "FAKE", "FAKE", "http://i0.kym-cdn.com/photos/images/original/000/006/467/4.Charmander.png", "FAKE", "FAKE", new Ability("fake","fake"), "FAKE", retreat ,"FAKE", "FAKE", "FAKE", "FAKE", "FAKE");
//        return nCard;
//    }

}
