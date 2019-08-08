package com.test.myapplication.viewModel;


import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.test.myapplication.R;
import com.test.myapplication.constants.EnumConstants;
import com.test.myapplication.data.dao.impl.OrderDAOImpl;
import com.test.myapplication.model.OrderDTO;
import com.test.myapplication.utils.StringUtils;
import com.test.myapplication.view.dialog.CustomDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CreateRecordViewModel extends AndroidViewModel {

    private final Context context;
    private final Activity activity;
    public MutableLiveData<Boolean> listClick = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> backUpClick = new MutableLiveData<Boolean>();
    public MutableLiveData<String> orderId = new MutableLiveData<String>();
    public MutableLiveData<String> orderOn = new MutableLiveData<String>();
    public MutableLiveData<String> productName = new MutableLiveData<String>();
    public MutableLiveData<String> seller = new MutableLiveData<String>();
    public MutableLiveData<String> status = new MutableLiveData<String>();
    public MutableLiveData<String> cp = new MutableLiveData<String>();
    public MutableLiveData<String> sp = new MutableLiveData<String>();
    public MutableLiveData<String> pickOn = new MutableLiveData<String>();
    public MutableLiveData<String> deliveron = new MutableLiveData<String>();
    public MutableLiveData<String> returnOn = new MutableLiveData<String>();
    public MutableLiveData<String> cancelOn = new MutableLiveData<String>();
    public MutableLiveData<String> creditamt = new MutableLiveData<String>();
    public MutableLiveData<String> creditOn = new MutableLiveData<String>();
    public MutableLiveData<String> profit = new MutableLiveData<String>();
    public OrderDTO orderDTO;
    public DatePickerDialog.OnDateSetListener dateChangeListener;
    CustomDialog  customDialog;

    public CreateRecordViewModel(@NonNull Application application, Activity activity) {
        super(application);
        this.context = application.getApplicationContext();
        this.activity = activity;
    }

    public void onListClick() {
        listClick.setValue(true);
    }

    public void onBackUp() {
        backUpClick.setValue(true);
    }

    public MutableLiveData<Boolean> getListClick() {
        return listClick;
    }

    public MutableLiveData<Boolean> getBackUpClick() {
        return backUpClick;
    }

    public void onStatusSelect() {
        List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.status_name));
        customDialog = new CustomDialog(activity, list, EnumConstants.dialogTagEnum.STATUS_TYPE);
        customDialog.show();
    }

    public void onStatusSelected(String sts) {
        if(customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        status.setValue(sts);
    }

    public void onDateSelect(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);
                        String date = String.format(Locale.getDefault(), "%1$te-%1$tb-%1$tY", calendar);
                        if(v.getId() == R.id.ll_orderon) {
                            orderOn.setValue(date);
                        } else if(v.getId() == R.id.ll_cancelon) {
                            cancelOn.setValue(date);
                        }else if(v.getId() == R.id.ll_crediton) {
                            creditOn.setValue(date);
                        }else if(v.getId() == R.id.ll_pickon) {
                            pickOn.setValue(date);
                        }else if(v.getId() == R.id.ll_returnon) {
                            returnOn.setValue(date);
                        }else if(v.getId() == R.id.ll_deliveron) {
                            deliveron.setValue(date);
                        }
                    }
                }, year, month, dayOfMonth);
       datePickerDialog.show();
    }

    public void onSave(View view) {
        if(StringUtils.isBlank(orderId.getValue())){
            Toast.makeText(context,context.getString(R.string.no_order_id),Toast.LENGTH_SHORT).show();
            return;
        }
        /*if(StringUtils.isBlank(cp.getValue()) && StringUtils.isBlank(creditamt.getValue()) && StringUtils.isBlank(creditOn.getValue()) &&
                StringUtils.isBlank(deliveron.getValue()) && StringUtils.isBlank(orderId.getValue()) && StringUtils.isBlank(orderOn.getValue()) &&
                StringUtils.isBlank(pickOn.getValue()) && StringUtils.isBlank(productName.getValue()) && StringUtils.isBlank(profit.getValue()) &&
                StringUtils.isBlank(returnOn.getValue()) && StringUtils.isBlank(seller.getValue()) && StringUtils.isBlank(sp.getValue()) &&
                StringUtils.isBlank(status.getValue())){
            Toast.makeText(context,context.getString(R.string.no_data),Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(orderDTO == null) {
            orderDTO = new OrderDTO();
        }
        orderDTO.setCp(cp.getValue());
        orderDTO.setCreditAmt(creditamt.getValue());
        orderDTO.setCreditOn(creditOn.getValue());
        orderDTO.setDeliverOn(deliveron.getValue());
        orderDTO.setOrderId(orderId.getValue());
        orderDTO.setOrderOn(orderOn.getValue());
        orderDTO.setPickedOn(pickOn.getValue());
        orderDTO.setProduct(productName.getValue());
        orderDTO.setProfit(profit.getValue());
        orderDTO.setReturnOn(returnOn.getValue());
        orderDTO.setSeller(seller.getValue());
        orderDTO.setSp(sp.getValue());
        orderDTO.setStatus(status.getValue());

        new OrderDAOImpl().insert(orderDTO);
        Toast.makeText(context,context.getString(R.string.saved),Toast.LENGTH_SHORT).show();
        clearData();
    }

    private void clearData() {
        cp.setValue("");
        creditamt.setValue("");
        creditOn.setValue("");
        deliveron.setValue("");
        orderId.setValue("");
        orderOn.setValue("");
        pickOn.setValue("");
        productName.setValue("");
        profit.setValue("");
        returnOn.setValue("");
        seller.setValue("");
        sp.setValue("");
        status.setValue("");
    }

    public void setData(OrderDTO orderDTO) {
        if (orderDTO != null) {
            this.orderDTO = orderDTO;
            cp.setValue(orderDTO.getCp());
            creditamt.setValue(orderDTO.getCreditAmt());
            creditOn.setValue(orderDTO.getCreditOn());
            deliveron.setValue(orderDTO.getDeliverOn());
            orderId.setValue(orderDTO.getOrderId());
            orderOn.setValue(orderDTO.getOrderOn());
            pickOn.setValue(orderDTO.getPickedOn());
            productName.setValue(orderDTO.getProduct());
            profit.setValue(orderDTO.getProfit());
            returnOn.setValue(orderDTO.getReturnOn());
            seller.setValue(orderDTO.getSeller());
            sp.setValue(orderDTO.getSp());
            status.setValue(orderDTO.getStatus());
        }
    }
}

