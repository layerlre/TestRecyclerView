package com.layernet.testrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.layernet.testrecyclerview.dummy.DummyContent;


public class ItemFragment extends Fragment {

    private int mRecyclerviewHeight;
    private MyItemRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the mAdapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyItemRecyclerViewAdapter(DummyContent.ITEMS,mRecyclerviewHeight);

            recyclerView.setAdapter(mAdapter);

            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (recyclerView.getHeight()>0){
                        mRecyclerviewHeight = recyclerView.getHeight();
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mAdapter.setRecyclerviewHeight(mRecyclerviewHeight);
                        mLayoutManager.scrollToPosition(mAdapter.getItemCount()-1);
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            String id = String.valueOf(++DummyContent.COUNT);
            String content = getString(R.string.lorem) + " " + id;
            String detail = "detail: " + id;
            DummyContent.DummyItem dummyItem = new DummyContent.DummyItem(id, content, detail);
            mAdapter.addItem(dummyItem);
            recyclerView.smoothScrollToPosition(DummyContent.COUNT);
        }
        return true;
    }
}
