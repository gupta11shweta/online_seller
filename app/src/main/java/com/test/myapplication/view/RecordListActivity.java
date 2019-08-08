package com.test.myapplication.view;

import android.os.Bundle;

import com.test.myapplication.R;
import com.test.myapplication.databinding.ActivityRecordListBinding;
import com.test.myapplication.model.OrderDTO;
import com.test.myapplication.model.SearchDTO;
import com.test.myapplication.view.adapter.RequestListAdapter;
import com.test.myapplication.viewModel.RecordListViewModel;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecordListActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ActivityRecordListBinding dataBinding;
    private RecordListViewModel viewModel;
    private RequestListAdapter adapter;
    private SearchDTO searchDTO = null;
    private String[] requestType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initAdapter(dataBinding.recyclerview);
    }

    private void initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_record_list);
        initViewModel();
        dataBinding.setViewModel(viewModel);
        dataBinding.setLifecycleOwner(this);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(RecordListViewModel.class);
        viewModel.getIsRefresh().observe(this, isDataAvailable -> {
            if(isDataAvailable != null && isDataAvailable) {
                updateList(viewModel.orderDTOList);
            }
        });
        dataBinding.searchView.setOnQueryTextListener(this);
        dataBinding.searchView.setOnCloseListener(this::onClose);
    }

    private void initAdapter(RecyclerView recyclerView) {
        adapter = new RequestListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setSearchQuery(String query) {
        adapter.getFilter().filter(query);
    }

    private void updateList(List<OrderDTO> orderDTOS) {
        adapter.setDataList(orderDTOS,viewModel);
        /*if(requestDTOList.isEmpty()){
            requestListViewModel.isDataAvailable.setValue(false);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderList();
    }

    public void getOrderList() {
        viewModel.getData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        setSearchQuery(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setSearchQuery(newText);
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }
}
