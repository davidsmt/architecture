package com.davidsmt.architecture.ui.pokecards;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.davidsmt.architecture.PokeApplication;
import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.ActivityPokecardsBinding;
import com.davidsmt.architecture.ui.pokecarddetails.PokeCardDetailsActivity;
import com.davidsmt.architecture.ui.pokecards.adapters.PokeCardsAdapter;
import com.davidsmt.architecture.ui.pokecards.contracts.PokeCardsContract;
import com.davidsmt.architecture.ui.pokecards.presenters.PokeCardsPresenter;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

import java.util.List;

public class PokeCardsActivity extends AppCompatActivity implements PokeCardsContract.View {

    private static final String TAG = "PokeCardsActivity";

    private PokeCardsContract.Presenter presenter;
    private ActivityPokecardsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokecards);
        initViews();

        new PokeCardsPresenter(
                this,
                PokeApplication.getUseCaseProvider().providePokeCardsUseCase()
        );
        presenter.start();
    }

    @Override
    public void setPresenter(PokeCardsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initViews() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            binding.progressBar.show();
        } else {
            binding.progressBar.hide();
        }
    }

    @Override
    public void showCards(List<PokeCardViewModel> pokeCardViewModelList) {
        binding.recyclerView.setAdapter(new PokeCardsAdapter(pokeCardViewModelList, new PokeCardsAdapter.Listener() {
            @Override
            public void onPokeCardClick(int position, PokeCardViewModel viewModel) {
                presenter.onPokeCardClick(position, viewModel);
            }
        }));
    }

    @Override
    public void showError() {
        Log.d(TAG, "Get cards error");
    }

    @Override
    public void goToPokeCardDetails(String cardId) {
        PokeCardDetailsActivity.startActivity(cardId, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
