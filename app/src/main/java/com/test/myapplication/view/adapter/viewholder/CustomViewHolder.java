package com.test.myapplication.view.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.test.myapplication.R;
import com.test.myapplication.model.MessageRequestDTO;
import com.test.myapplication.view.adapter.genericadapter.GenericViewHolder;

import org.greenrobot.eventbus.EventBus;

public class CustomViewHolder extends GenericViewHolder {
    private static final String TAG = CustomViewHolder.class.getSimpleName();
    private TextView tvCustomName;
    private String tag = "";
    private Object element;

    public CustomViewHolder(View itemView) {
        super(itemView);
        tvCustomName = itemView.findViewById(R.id.tvCustomName);
        tvCustomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageRequestDTO(tag, element));
            }
        });
    }

    @Override
    public void bindData(Object element1, int position, String tag, String selectedId) {
        try {
            this.tag = tag;
            this.element = element1;
            tvCustomName.setText(element.toString());

        } catch (Exception e) {
            Log.e(TAG, "bindData: ", e);
        }
    }
}
