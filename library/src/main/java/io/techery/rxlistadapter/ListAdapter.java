package io.techery.rxlistadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Simple {@code Adapter} with underlying {@code List} support.
 *
 * @param <T> {@code List} item type
 */
public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Context context;
    protected final List<T> items;

    public ListAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public List<T> getItems() {
        return items;
    }

}
