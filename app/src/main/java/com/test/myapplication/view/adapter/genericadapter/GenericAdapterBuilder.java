package com.test.myapplication.view.adapter.genericadapter;

import java.util.ArrayList;

public class GenericAdapterBuilder {
    private final ArrayList<GenericAdapterModel> lModels = new ArrayList<>();
    private boolean mIsFilterEnabled = false;
    private boolean mIsMultiViewTypeEnabled = false;

    public GenericAdapterBuilder() {
    }

    public GenericAdapterBuilder addModel(GenericAdapterModel model) {
        lModels.add(model);
        return this;
    }

    public GenericAdapterBuilder addModel(int pLayout, Class<?> pViewHolder, Class<?> pItemType, String pTag) {
        lModels.add(new GenericAdapterModel(pLayout, pViewHolder, pItemType, pTag));
        return this;
    }

    public GenericRecyclerAdapter execute() {
        if (mIsFilterEnabled) {
            return new GenericRecyclerAdapter(lModels, false, true);
        } else if (mIsMultiViewTypeEnabled) {
            return new GenericRecyclerAdapter(lModels, true, false);
        } else {
            return new GenericRecyclerAdapter(lModels);
        }
    }

    public GenericAdapterBuilder setFilterEnabled() {
        mIsFilterEnabled = true;
        return this;
    }

    public GenericAdapterBuilder setMultiViewTypeEnabled() {
        mIsMultiViewTypeEnabled = true;
        return this;
    }
}
