package com.test.myapplication.view.adapter.genericadapter.Interface;


public interface GenericViewHolderViewTypeCallBack  {

    /**
     * Returns the viewType used by the KRecyclerViewAdapter.
     * @return An int value for your viewType used by GenericRecyclerAdapter.
     */
    int recyclerItemViewType(int itemPosition, Object itemObject);
}
