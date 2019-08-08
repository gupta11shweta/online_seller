package com.test.myapplication.view.adapter.filter;

import android.widget.Filter;

import com.test.myapplication.model.OrderDTO;
import com.test.myapplication.view.adapter.genericadapter.GenericRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SearchFilter extends Filter {

    private GenericRecyclerAdapter mAdapter;

    public SearchFilter(GenericRecyclerAdapter pAdapter) {
        mAdapter = pAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        final List<Object> list = mAdapter.mOriginalObjectList;

        final ArrayList<Object> filteredList = new ArrayList<>();

        String filterableString = "";

        for (Object lModel : list) {
            if (lModel instanceof OrderDTO) {
                filterableString = ((OrderDTO) lModel).getStatus();
            }
            if (filterableString.toLowerCase().contains(constraint.toString().toLowerCase())) {
                filteredList.add(lModel);
            }
        }

        results.values = filteredList;
        results.count = filteredList.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.mObjectList = (List<Object>) results.values;
        mAdapter.notifyDataSetChanged();
    }
}