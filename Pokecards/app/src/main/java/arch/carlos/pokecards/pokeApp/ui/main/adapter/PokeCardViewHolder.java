package arch.carlos.pokecards.pokeApp.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import arch.carlos.pokecards.databinding.LayoutPokecardItemBinding;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 8/21/17.
 */

public class PokeCardViewHolder extends RecyclerView.ViewHolder{

    private final LayoutPokecardItemBinding mBinding;
    private PokeCard mModel;

    public PokeCardViewHolder(LayoutPokecardItemBinding binding, PokeCardListAdapter.PokeCardEventListener listener) {
        super(binding.getRoot());
        this.mBinding = binding;
        mBinding.getRoot().setOnClickListener(view -> listener.onClick(mModel));
    }

    public void bind(PokeCard itemViewModel, int position){
        mModel = itemViewModel;
        mBinding.setViewModel(itemViewModel);
        mBinding.executePendingBindings();

    }

    public LayoutPokecardItemBinding getmBinding() {
        return mBinding;
    }
}

