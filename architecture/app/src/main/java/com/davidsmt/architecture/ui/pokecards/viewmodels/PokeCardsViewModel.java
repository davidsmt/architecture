package com.davidsmt.architecture.ui.pokecards.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import com.davidsmt.architecture.ui.pokecards.fragments.CardDetailsFragment;

/**
 * Created by d.san.martin.torres on 7/17/17.
 */

public class PokeCardsViewModel extends ViewModel {

    private MutableLiveData<Transition> transition;

    public PokeCardsViewModel() {
        transition = new MutableLiveData<>();
        transition.setValue(new Transition(Transition.State.Cards));
    }

    public void onCardDetail(PokeCardViewModel viewModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CardDetailsFragment.ARG_VIEWMODEL, viewModel);
        transition.setValue(new Transition(Transition.State.CardDetail, bundle));
    }

    public void onCards() {
        transition.setValue(new Transition(Transition.State.Cards));
    }

    public LiveData<Transition> getTransition() {
        return transition;
    }

    public static class Transition {

        private State state;
        private Bundle bundle;

        public Transition(State state) {
            this.state = state;
        }

        public Transition(State state, Bundle bundle) {
            this.state = state;
            this.bundle = bundle;
        }

        public State getState() {
            return state;
        }

        public Bundle getBundle() {
            return bundle;
        }

        public enum State {
            Cards,
            CardDetail;
        }
    }
}
