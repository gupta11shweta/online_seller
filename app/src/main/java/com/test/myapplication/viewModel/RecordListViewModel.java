package com.test.myapplication.viewModel;

import com.test.myapplication.data.dao.impl.OrderDAOImpl;
import com.test.myapplication.model.OrderDTO;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecordListViewModel extends ViewModel {

    private static final String TAG = RecordListViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> isDataAvailable = new MutableLiveData();
    public MutableLiveData<Boolean> progressBar = new MutableLiveData();
    public MutableLiveData<Boolean> search = new MutableLiveData<>();
    public List<OrderDTO> orderDTOList;
    public MutableLiveData<String> titleText = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    public MutableLiveData<String> titleCount = new MutableLiveData<>();

    public RecordListViewModel() {
        orderDTOList = new ArrayList<>();
        search.setValue(false);
        isDataAvailable.setValue(false);
        isRefresh.setValue(false);
    }

    public void requestTitle(String requestTitle) {
        titleText.setValue(requestTitle);
    }

    public void setData(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
        if(orderDTOList == null || orderDTOList.isEmpty()) {
            isDataAvailable.setValue(false);
            isRefresh.setValue(false);
        } else {
            isDataAvailable.setValue(true);
            isRefresh.setValue(true);
        }
    }

    public MutableLiveData<Boolean> getIsDataAvailable() {
        return isDataAvailable;
    }

    public void getData() {
        progressBar.setValue(true);
        List<OrderDTO> mList = new OrderDAOImpl().getAll();
        setData(mList);
        progressBar.setValue(false);
    }

    public MutableLiveData<Boolean> getIsRefresh() {
        return isRefresh;
    }

    public MutableLiveData<String> getTitleCount() {
        return titleCount;
    }

    /* public void onSearch(View view){

    }*/
}
