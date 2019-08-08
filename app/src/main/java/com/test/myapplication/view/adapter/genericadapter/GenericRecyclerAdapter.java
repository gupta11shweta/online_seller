package com.test.myapplication.view.adapter.genericadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.test.myapplication.view.adapter.genericadapter.Interface.GenericViewHolderViewTypeCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GenericRecyclerAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private static final String TAG = GenericRecyclerAdapter.class.getSimpleName();
    public List mObjectList;
    public List mOriginalObjectList;
    private ArrayList<GenericAdapterModel> mModels;
    private Filter mFilter;
    private boolean mIsFilterEnabled;
    private boolean mIsMultiViewTypeEnabled;
    private GenericViewHolderViewTypeCallBack mViewTypeCallBack;

    public GenericRecyclerAdapter(ArrayList<GenericAdapterModel> pModels) {
        mModels = pModels;
    }

    public GenericRecyclerAdapter(ArrayList<GenericAdapterModel> pModels, GenericViewHolderViewTypeCallBack viewTypeCallBack) {
        mModels = pModels;
        mViewTypeCallBack = viewTypeCallBack;
    }

    public GenericRecyclerAdapter(ArrayList<GenericAdapterModel> pModels, boolean pIsMultiViewTypeEnabled, boolean pIsFilterEnabled) {
        mModels = pModels;
        mIsFilterEnabled = pIsFilterEnabled;
        mIsMultiViewTypeEnabled = pIsMultiViewTypeEnabled;
    }

    public GenericRecyclerAdapter(int pLayout, Class pViewHolder, Class pItemType, String pTag) {
        mModels = new ArrayList<>();
        mModels.add(new GenericAdapterModel(pLayout, pViewHolder, pItemType, pTag));
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        GenericViewHolder holder = null;
        if (viewType != -1) {
            GenericAdapterModel lModel = mModels.get(viewType);
            View lView = inflater.inflate(lModel.getLayout(), parent, false);
            try {
                Class mClass = lModel.getViewHolder();
                holder = (GenericViewHolder) mClass.getConstructor(View.class).newInstance(lView);
            } catch (Exception pE) {
                Log.e(TAG, "onCreateViewHolder: ", pE);
                throw new RuntimeException(pE.getMessage());
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.bindData(mObjectList.get(position), position, mModels.get(0).getTag(), mModels.get(0).getSelectedId());
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsMultiViewTypeEnabled) {
            return mViewTypeCallBack.recyclerItemViewType(position, mObjectList.get(position));
        } else {
            Class<?> lItemClass = mObjectList.get(position).getClass();
            for (int i = 0; i < mModels.size(); i++) {
                if (mModels.get(i).getItemType().equals(lItemClass)) {
                    return i;
                }
            }
        }
        return -1;

    }

    @Override
    public int getItemCount() {
        return mObjectList != null ? mObjectList.size() : 0;
    }

    public void setList(List pObjectList) {
        if (pObjectList == null)
            return;
        createIfNull();
        this.mObjectList.clear();
        mObjectList.addAll(pObjectList);
        if (mIsFilterEnabled) {
            mOriginalObjectList = pObjectList;
        }
        notifyDataSetChanged();
    }

    public List getItems() {
        return mObjectList;
    }

    public void addNewRows(List pObjectList) {
        if (pObjectList == null)
            return;
        createIfNull();
        mObjectList.addAll(pObjectList);
        if (mIsFilterEnabled) {
            mOriginalObjectList = mObjectList;
        }
        notifyDataSetChanged();
    }

    public void addNewRows(Object pObject) {
        if (pObject == null)
            return;
        createIfNull();
        mObjectList.add(pObject);
        if (mIsFilterEnabled) {
            mOriginalObjectList = mObjectList;
        }
        notifyDataSetChanged();
    }

    private void createIfNull() {
        if (this.mObjectList == null) {
            this.mObjectList = new ArrayList();
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    public void setFilter(Filter pFilter) {
        mFilter = pFilter;
    }

    public GenericViewHolderViewTypeCallBack getMultiViewType() {
        return mViewTypeCallBack;
    }

    public void setMultiViewType(GenericViewHolderViewTypeCallBack mViewTypeCallBack) {
        this.mViewTypeCallBack = mViewTypeCallBack;
    }
}
