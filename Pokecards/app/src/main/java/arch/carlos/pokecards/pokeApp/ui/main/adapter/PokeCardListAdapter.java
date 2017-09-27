package arch.carlos.pokecards.pokeApp.ui.main.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.utils.DiffAdapter;
import arch.carlos.pokecards.databinding.LayoutPokecardItemBinding;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 8/21/17.
 */


public class PokeCardListAdapter extends RecyclerView.Adapter<PokeCardViewHolder> {
    List<PokeCardViewHolder> holders;
    public List<PokeCard> items;
    private PokeCardEventListener mListener;

    public PokeCardListAdapter(PokeCardEventListener clickListener) {
        this.items = new ArrayList<>();
        mListener = clickListener;
        holders = new ArrayList<>();
    }


    @Override
    public PokeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        LayoutPokecardItemBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.layout_pokecard_item, parent, false);

        return new PokeCardViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final PokeCardViewHolder holder, final int position) {
        holder.bind(items.get(position),position);
        holders.add(holder);


    }


    public void differItems(List<PokeCard> nItems) {
        DiffAdapter<PokeCard> nDiff = new DiffAdapter<>(nItems,items);
        this.items.clear();
        this.items.addAll(nItems);
        nDiff.updateList(this);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface PokeCardEventListener {
        void onClick(PokeCard card);
    }

}