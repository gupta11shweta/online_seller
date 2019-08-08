package com.test.myapplication.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.myapplication.R;
import com.test.myapplication.constants.EnumConstants;
import com.test.myapplication.databinding.DialogCustomBinding;
import com.test.myapplication.view.CreateRecordActivity;
import com.test.myapplication.view.RecordListActivity;
import com.test.myapplication.view.adapter.filter.SearchFilter;
import com.test.myapplication.view.adapter.genericadapter.GenericAdapterBuilder;
import com.test.myapplication.view.adapter.genericadapter.GenericRecyclerAdapter;
import com.test.myapplication.view.adapter.viewholder.CustomViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CustomDialog extends Dialog{
    private List<?> dialogDTOList;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Activity context;
    private TextView tvNodata;
    private GenericRecyclerAdapter mAdapter;
    private EnumConstants.dialogTagEnum dialogTagEnum;
    private DialogCustomBinding dataBinding;

    public CustomDialog(@NonNull Activity context, List<?> dialogDTOList, EnumConstants.dialogTagEnum cominFrom) {
        super(context);
        this.context = context;
        this.dialogDTOList = dialogDTOList;
        dialogTagEnum = cominFrom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initViews();
        initAdapter();
    }

    private void initViews() {
        etSearch = dataBinding.etSearch;
        tvNodata = dataBinding.tvNoData;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mAdapter.getFilter().filter(editable.toString(), pI -> {
                    if (pI != 0) {
                        tvNodata.setVisibility(View.GONE);
                        if (mAdapter.mObjectList == null || mAdapter.mObjectList.size() <= 0) {
                            //If the search key is deleted, set the original list
                            mAdapter.addNewRows(mAdapter.mOriginalObjectList);
                        }
                    }else{
                        tvNodata.setVisibility(View.VISIBLE);
                    }

                });
            }
        });
        /*dataBinding.ivCancel.setOnClickListener(v -> dismiss());
        dataBinding.ivSearch.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        });*/
    }


    private void initDataBinding() {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_custom, null, false);
        setContentView(dataBinding.getRoot());
        if (dialogTagEnum == EnumConstants.dialogTagEnum.STATUS_TYPE) {
            dataBinding.setLifecycleOwner((CreateRecordActivity) context);
        }else {
            dataBinding.setLifecycleOwner((RecordListActivity) context);
        }
    }

    private void initAdapter() {
        if (dialogDTOList.size() > 0) {
            Class objectClass = getClassFromTag();
            recyclerView = dataBinding.recyclerview;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, RecyclerView.VERTICAL));
            mAdapter = new GenericAdapterBuilder().addModel(
                    R.layout.row_item_custom, //set your row's layout file
                    CustomViewHolder.class, //set your view holder class
                    objectClass,// set your model class(If you use just String list, it can be just String.class)
                    dialogTagEnum.value())
                    .setFilterEnabled()
                    .execute();
            mAdapter.setFilter(new SearchFilter(mAdapter));
            recyclerView.setAdapter(mAdapter);
            mAdapter.setList(dialogDTOList);
        }
    }

    public Class getClassFromTag() {
        Class oobj = null;
        switch (dialogTagEnum) {
            case STATUS_TYPE:
                oobj = String.class;
                break;
        }
        return oobj;
    }
}
