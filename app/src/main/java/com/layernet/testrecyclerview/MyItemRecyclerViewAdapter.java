package com.layernet.testrecyclerview;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.layernet.testrecyclerview.dummy.DummyContent.DummyItem;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<DummyItem> mValues;
    private int recyclerviewHeight = 0;

    public void setRecyclerviewHeight(int recyclerviewHeight) {
        this.recyclerviewHeight = recyclerviewHeight;
        notifyDataSetChanged();
    }

    public MyItemRecyclerViewAdapter(List<DummyItem> items,int recyclerviewHeight) {
        mValues = items;
        this.recyclerviewHeight = recyclerviewHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        if (position == mValues.size() - 1){
            holder.emptyLayout.getLayoutParams().height = recyclerviewHeight;
        }else {
            holder.emptyLayout.getLayoutParams().height = 0;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(DummyItem item) {
        mValues.add(item);
        notifyItemInserted(mValues.size() - 1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemChanged(mValues.size() - 2);
            }
        }, 400);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final View emptyLayout;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            emptyLayout = (View) view.findViewById(R.id.empty_layout);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
