package arch.carlos.pokecards.pokeApp.ui.main.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.adapter.DiffAdapter;
import arch.carlos.pokecards.baseArch.utils.ListDifferUtil;
import arch.carlos.pokecards.databinding.LayoutPokecardItemBinding;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 8/21/17.
 */


public class PokeCardListAdapter extends DiffAdapter<PokeCard, PokeCardViewHolder> {

    private PokeCardEventListener mListener;


    public PokeCardListAdapter(PokeCardEventListener clickListener) {
        super();
        mListener = clickListener;
    }


    @Override
    public PokeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        LayoutPokecardItemBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.layout_pokecard_item, parent, false);

        return new PokeCardViewHolder(binding,mListener);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final PokeCardViewHolder holder, final int position) {
        holder.bind(currentItems.get(position),position);
    }

    public interface PokeCardEventListener {
        void onClick(PokeCard card);
    }

}