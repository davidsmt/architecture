package arch.carlos.pokecards.baseArch.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.baseArch.cache.DomainCache;

/**
 * Created by mobgen on 9/26/17.
 */

public class DiffAdapter<T extends DomainCache.Cacheable>{

    final DiffUtil.DiffResult operations;

    public DiffAdapter(List<T> newITems, List<T> oldItems){
        operations = DiffUtil.calculateDiff(new CacheableDiffCallback(newITems,oldItems));
    }
    public void updateList(RecyclerView.Adapter updateListener) {
        operations.dispatchUpdatesTo(updateListener);
    }

    private class CacheableDiffCallback extends DiffUtil.Callback{

        List<T> oldItems;
        List<T> newItems;

        public CacheableDiffCallback(List<T> newItems, List<T> oldItems) {
            this.newItems = newItems;
            this.oldItems = oldItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).getPrimaryKey().equals(newItems.get(newItemPosition).getPrimaryKey());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            //you can return particular field for changed item.
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }


}
