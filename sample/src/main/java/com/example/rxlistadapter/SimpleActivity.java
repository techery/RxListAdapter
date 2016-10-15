package com.example.rxlistadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

public class SimpleActivity extends AppCompatActivity {

    @BindView(R.id.list) RecyclerView recyclerView;
    PublishSubject<String> pipe = PublishSubject.create();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);
        //
        RecyclerView.Adapter adapter = new StringAdapter(this, provideSource());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Observable<List<String>> provideSource() {
        List<String> initial = Arrays.asList("Hello", "Simple", "Sample", "Data");
        Observable<List<String>> source = pipe.scan(new ArrayList<>(initial), (strings, s) -> {
            strings.add(s);
            return strings;
        });
        return source;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                pipe.onNext(String.valueOf(System.currentTimeMillis()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
