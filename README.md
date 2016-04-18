## RxListAdapter
Easy to integrate `RecyclerView.Adapter` with `Rx` support

## Overview
Lib consists of adapter delegate (`RxAdapterBridge`) which links source `Observable` with `RecyclerView`'s `Adapter`.
Also, there are basic implementations for both delegate and adapter, so it's ready to use for common situations.

## Getting started

### Simple use-case
1. Extend `SimpleRxListAdapter`, e.g.:

    ```java
    public class StringAdapter extends SimpleRxListAdapter<String, StringAdapter.StringViewHolder> {
    
        public StringAdapter(Context context, Observable<List<String>> source) {
            super(context, source);
        }
        
        ...
    }
    ```
2. Use it

    ```java
    Observable<List<String>> source = Observable
                    .from(new String[]{"Hello", "Simple", "Sample", "Data"})
                    .toList();
    RecyclerView.Adapter adapter = new StringAdapter(context, source);
    recyclerView.setAdapter(adapter);
    ```

See [Sample](sample/src/main/java/com.example.rxlistadapter) for details.

### Customization
1. Extend `RxAdapterBridge` to implement `abstract Subscriber<O> subscriber()` method:

    ```java
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
    ```
    
2. Integrate `RxAdapterBridge` into your adapter:
    
    ```java
    public class SimpleRxListAdapter<T, VH extends RecyclerView.ViewHolder> extends ListAdapter<T, VH> {
    
        final RxAdapterBridge<T, List<T>> rxBridge;
    
        public SimpleRxListAdapter(Context context, Observable<List<T>> source) {
            super(context, new ArrayList<T>(), source);
            rxBridge = new SimpleRxAdapterBridge<T>(this, source, this.items);
        }
    
        @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            rxBridge.onAttachedToRecyclerView(recyclerView);
        }
    
        @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            rxBridge.onDetachedFromRecyclerView(recyclerView);
        }
    
    }
    ```


## Dev. status
Tested in production.

![JitPack release](https://img.shields.io/github/tag/techery/RxListAdapter.svg?label=JitPack)

## Installation
```groovy
repositories {
    maven { url "https://jitpack.io" }
}
dependencies {
    compile 'com.github.techery:RxListAdapter:{latestVersion}'
}
```

## License

    Copyright (c) 2016 Techery

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

