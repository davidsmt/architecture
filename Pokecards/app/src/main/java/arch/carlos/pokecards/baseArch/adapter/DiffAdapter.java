package arch.carlos.pokecards.baseArch.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.baseArch.utils.ListDifferUtil;

/**
 * Created by mobgen on 9/27/17.
 *
 * DiffAdater uses ListDifferUtil to get the diff between the new and old items, and then notify each item change, including moved items
 */


public abstract class DiffAdapter<T extends DomainCache.Cacheable, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> currentItems;

    public DiffAdapter(){
        this.currentItems = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return currentItems.size();
    }

    /**
     * @param nItems new items the recycler will show
     */
    public void differUpdate(List<T> nItems){
        this.currentItems = nItems;
        notifyDataSetChanged();
    }
}
