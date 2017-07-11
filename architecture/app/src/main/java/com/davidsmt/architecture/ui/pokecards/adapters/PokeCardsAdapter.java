package com.davidsmt.architecture.ui.pokecards.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidsmt.architecture.R;
import com.davidsmt.architecture.databinding.LayoutPokecardItemBinding;
import com.davidsmt.architecture.ui.viewmodels.PokeCardViewModel;

import java.util.List;

/**
 * Created by d.san.martin.torres on 7/10/17.
 */

public class PokeCardsAdapter extends RecyclerView.Adapter<PokeCardsAdapter.PokeCardViewHolder> {

    private List<PokeCardViewModel> items;
    private Listener listener;

    public PokeCardsAdapter(@NonNull List<PokeCardViewModel> items, @NonNull Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public PokeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutPokecardItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_pokecard_item,
                parent,
                false);
        return new PokeCardViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(PokeCardViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PokeCardViewHolder extends RecyclerView.ViewHolder {

        private PokeCardViewModel viewModel;
        private LayoutPokecardItemBinding binding;

        PokeCardViewHolder(LayoutPokecardItemBinding binding, final Listener listener) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPokeCardClick(getAdapterPosition(), viewModel);
                }
            });
        }

        void bind(PokeCardViewModel viewModel) {
            this.viewModel = viewModel;

            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }

    }

    public interface Listener {
        void onPokeCardClick(int position, PokeCardViewModel viewModel);
    }
}
