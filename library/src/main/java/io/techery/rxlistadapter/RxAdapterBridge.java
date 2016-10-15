package io.techery.rxlistadapter;

import android.support.v7.widget.RecyclerView;

import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.support.v7.widget.RecyclerView.Adapter;

/**
 * Delegate to safely connect {@link Adapter} with {@link Observable}.
 *
 * @param <T> Underlying {@link List} item type
 * @param <O> Source {@link Observable} type, usually {@code List<T>}.
 */
public abstract class RxAdapterBridge<T, O> {
    protected final Adapter adapter;
    protected final List<T> items;
    protected final Observable<O> source;
    private Subscription subscription;

    public RxAdapterBridge(Adapter adapter, Observable<O> source) {
        this(adapter, source, new ArrayList<T>());
    }

    public RxAdapterBridge(Adapter adapter, Observable<O> source, List<T> items) {
        this.adapter = adapter;
        this.source = source;
        this.items = items;
    }

    /**
     * Attaches source {@link Observable} subscription lifecycle.<p>
     * Should be called from {@link Adapter#onAttachedToRecyclerView(RecyclerView)}
     *
     * @param recyclerView to bind source {@link Observable} to
     */
    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (subscription != null) unsubscribe();
        subscription = bindObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.<O>bindView(recyclerView))
                .subscribe(subscriber());
    }

    /**
     * Call to unsubscribe from source {@link Observable}.<p>
     * Should be called from {@link Adapter#onDetachedFromRecyclerView(RecyclerView)}
     *
     * @param recyclerView to bind source {@link Observable} to
     */
    public final void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        unsubscribe();
    }

    private Observable<O> bindObservable() {
        Observable.Transformer<O, O> t = decorator();
        if (t == null) return source;
        else return source.compose(t);
    }

    private void unsubscribe() {
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    /**
     * Add optional logic to source {@code Observable} before it's been subscribed
     *
     * @return {@link Observable.Transformer} to compose source {@code Observable} with
     */
    protected Observable.Transformer<O, O> decorator() {
        return null;
    }

    /**
     * React on emission, e.g. modify {@code items}, call {@link Adapter#notifyDataSetChanged()}, etc.<p>
     * It's guaranteed it's called on main thread.
     *
     * @return {@link Subscriber} to react on emissions
     */
    protected abstract Subscriber<O> subscriber();
}
