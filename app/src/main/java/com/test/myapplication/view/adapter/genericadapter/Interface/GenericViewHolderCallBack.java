package com.test.myapplication.view.adapter.genericadapter.Interface;

import android.view.ViewGroup;

import com.test.myapplication.view.adapter.genericadapter.GenericViewHolder;


public interface GenericViewHolderCallBack {

    /**
     * Returns the holder used by the KRecyclerViewAdapter.
     * @return An instance of GenericViewHolder used by GenericRecyclerAdapter.
     */
    GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
}
