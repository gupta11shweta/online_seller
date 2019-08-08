package com.test.myapplication.view.adapter.genericadapter;

public class GenericAdapterModel {
    private int mLayout;
    private Class mViewHolder;
    private Class mItemType;
    private String mTag;
    private String selectedId="";

    public GenericAdapterModel(int pLayout, Class pViewHolder, Class pItemType, String pTag) {
        mLayout = pLayout;
        mViewHolder = pViewHolder;
        mItemType = pItemType;
        mTag = pTag;
    }

    public Class getItemType() {
        return mItemType;
    }

    public void setItemType(Class pItemType) {
        mItemType = pItemType;
    }

    public int getLayout() {
        return mLayout;
    }

    public void setLayout(int pLayout) {
        mLayout = pLayout;
    }

    public Class getViewHolder() {
        return mViewHolder;
    }

    public void setViewHolder(Class pViewHolder) {
        mViewHolder = pViewHolder;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }
}
