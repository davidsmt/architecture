package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.ArchActivity;
import arch.carlos.pokecards.baseArch.ArchApplication;
import arch.carlos.pokecards.baseArch.vo.Resource;
import arch.carlos.pokecards.baseArch.vo.Status;
import arch.carlos.pokecards.databinding.FragmentCardsBinding;
import arch.carlos.pokecards.pokeApp.ui.main.adapter.PokeCardListAdapter;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Extends ArchActivity to access viewModelFactory and link the list of pokecards to a liveData, handled by the viewModel
 */
public class MainActivity extends ArchActivity {

    FragmentCardsBinding mBinding;
    PokeCardListViewModel mViewModel;
    PokeCardListAdapter mAdapter;


    @Override
    protected void initActivity() {
    }

    @Override
    protected void initView() {
        //Set the activity view
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_cards);

        //Set the adapter view
        LinearLayoutManager llm = new LinearLayoutManager(ArchApplication.getContext());
        mBinding.recyclerView.setLayoutManager(llm);
        mAdapter = new PokeCardListAdapter(this::deleteItem);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewModel() {
        //Set the viewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(PokeCardListViewModel.class);

        //Link to the viewModel LiveData
        mViewModel.getPokeCardList().observe(this, this::showCards);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showCards(Resource<List<PokeCard>> cards) {

        //Shows or hides the loading view
        mBinding.progressBar.setVisibility(cards.status == Status.SUCCESS ? View.GONE : View.VISIBLE);

        //Updates the list view
        if (cards.data != null) {
            mAdapter.differUpdate(cards.data);

        }
    }


    // Deletes the item from the viewModel
    private void deleteItem(PokeCard card) {
        mViewModel.deleteCard(card, true, true);
    }

}
