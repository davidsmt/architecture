package com.davidsmt.architecture.ui.pokecarddetails;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.davidsmt.architecture.PokeApplication;
import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.ActivityPokeCardDetailsBinding;
import com.davidsmt.architecture.ui.pokecarddetails.presenters.PokeCardDetailsPresenter;
import com.davidsmt.architecture.ui.pokecarddetails.views.PokeCardDetailsContract;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

public class PokeCardDetailsActivity extends AppCompatActivity implements PokeCardDetailsContract.View {

    private static final String TAG = "PokeCardDetailsActivity";
    private static final String CARD_ID = "CARD_ID";

    public static void startActivity(String cardId, Activity activity) {
        Intent intent = new Intent(activity, PokeCardDetailsActivity.class);
        intent.putExtra(CARD_ID, cardId);
        activity.startActivity(intent);
    }

    private ActivityPokeCardDetailsBinding binding;
    private PokeCardDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_poke_card_details);

        new PokeCardDetailsPresenter(
                this,
                getIntent().getStringExtra(CARD_ID),
                PokeApplication.getUseCaseProvider().providePokeCardUseCase()
        );
        presenter.start();
    }

    @Override
    public void setPresenter(PokeCardDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCard(PokeCardViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    public void showError() {
        Log.d(TAG, "Get card error");
    }

}
