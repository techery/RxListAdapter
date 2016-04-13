package io.techery.rxlistadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Simple Adapter with {@code RxAdapterBridge} impl. to replace all items for adapter on every Observable change
 *
 * @param <T> {@code List} item type
 * @see SimpleRxAdapterBridge
 */
public abstract class SimpleRxListAdapter<T, VH extends RecyclerView.ViewHolder> extends RxListAdapter<T, List<T>, VH> {

    public SimpleRxListAdapter(Context context, Observable<List<T>> source) {
        super(context, new ArrayList<T>(), source);
    }

    @Override public RxAdapterBridge<T, List<T>> createRxAdapterBridge(List<T> items, Observable<List<T>> source) {
        return new SimpleRxAdapterBridge<T>(this, source, items);
    }

}
