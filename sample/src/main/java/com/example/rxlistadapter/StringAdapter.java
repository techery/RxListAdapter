package com.example.rxlistadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.techery.rxlistadapter.SimpleRxListAdapter;

public class StringAdapter extends SimpleRxListAdapter<String, StringAdapter.StringViewHolder> {

    private final LayoutInflater inflater;

    public StringAdapter(Context context, rx.Observable<List<String>> source) {
        super(context, source);
        inflater = LayoutInflater.from(context);
    }

    @Override public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return  new StringViewHolder(itemView);
    }

    @Override public void onBindViewHolder(StringViewHolder holder, int position) {
        String item = getItem(position);
        ((TextView) holder.itemView).setText(item);
    }

    static class StringViewHolder extends RecyclerView.ViewHolder {

        public StringViewHolder(View itemView) {
            super(itemView);
        }
    }
}
