package io.techery.rxlistadapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Simple {@code RxAdapterBridge} impl. to replace all items for adapter on every Observable change
 *
 * @param <T> {@code List} item type
 */
public class SimpleRxAdapterBridge<T> extends RxAdapterBridge<T, List<T>> {

    public SimpleRxAdapterBridge(RecyclerView.Adapter adapter, Observable<List<T>> source, List<T> items) {
        super(adapter, source, items);
    }

    @Override protected Subscriber<List<T>> subscriber() {
        return new Subscriber<List<T>>() {
            @Override public void onNext(List<T> newItems) {
                items.clear();
                items.addAll(newItems);
                adapter.notifyDataSetChanged();
            }

            @Override public void onCompleted() {
            }

            @Override public void onError(Throwable e) {
            }
        };
    }
}
