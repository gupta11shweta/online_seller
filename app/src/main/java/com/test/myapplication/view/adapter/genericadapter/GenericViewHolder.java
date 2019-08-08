package com.test.myapplication.view.adapter.genericadapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericViewHolder<M> extends RecyclerView.ViewHolder {
    public GenericViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(M element, int position, String tag, String selectedId);

}
