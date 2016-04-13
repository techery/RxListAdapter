package io.techery.rxlistadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import rx.Observable;

/**
 * Base Adapter with {@link RxAdapterBridge} support to link {@code Adapter} with {@code Observable}
 *
 * @param <T> List item type
 * @param <O> Observable item type
 */
public abstract class RxListAdapter<T, O, VH extends RecyclerView.ViewHolder> extends ListAdapter<T, VH> {

    protected final RxAdapterBridge<T, O> rxBridge;

    public RxListAdapter(Context context, List<T> items, Observable<O> source) {
        super(context, items);
        rxBridge = createRxAdapterBridge(items, source);
    }

    protected abstract RxAdapterBridge<T, O> createRxAdapterBridge(List<T> items, Observable<O> source);

    @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        rxBridge.onAttachedToRecyclerView(recyclerView);
    }

    @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        rxBridge.onDetachedFromRecyclerView(recyclerView);
    }

}
