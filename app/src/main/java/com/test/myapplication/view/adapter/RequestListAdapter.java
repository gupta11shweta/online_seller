package com.test.myapplication.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.test.myapplication.MyApplication;
import com.test.myapplication.R;
import com.test.myapplication.constants.AppConstants;
import com.test.myapplication.databinding.RowItemAutoCompleteBinding;
import com.test.myapplication.databinding.RowItemOrderListBinding;
import com.test.myapplication.handler.OnClickHandler;
import com.test.myapplication.model.OrderDTO;
import com.test.myapplication.utils.StringUtils;
import com.test.myapplication.view.CreateRecordActivity;
import com.test.myapplication.viewModel.RecordListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.MyViewHolder> implements Filterable {

    private LayoutInflater layoutInflater;
    private List<OrderDTO> mList;
    private List<OrderDTO> mListFiltered;
    private Context context;
    private boolean searchMatch = false;
    public RecordListViewModel viewModel;
    private int selectedCount = 0;


    public RequestListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RequestListAdapter.MyViewHolder viewHolder;
        if (viewType == 1) {
            RowItemOrderListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_order_list, parent, false);
            viewHolder = new RequestListAdapter.MyViewHolder(binding);
        } else {
            RowItemAutoCompleteBinding binding;
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_auto_complete, parent, false);
            viewHolder = new RequestListAdapter.MyViewHolder(binding);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mListFiltered.get(position) instanceof OrderDTO ? 1 : 2;
    }

    public void setDataList(List<OrderDTO> dtoList, RecordListViewModel viewModel) {
        this.mList = dtoList;
        this.mListFiltered = dtoList;
        this.viewModel = viewModel;
        viewModel.getTitleCount().observe((AppCompatActivity) context, s -> {
            if (StringUtils.isNotBlank(s)) {
                selectedCount = Integer.parseInt(s);
            }
        });
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setOrderDTO(mListFiltered.get(position));
        holder.binding.setViewmodel(viewModel);
    }

    @Override
    public int getItemCount() {
        return mListFiltered != null ? mListFiltered.size() : 0;
    }


    @BindingAdapter("statuscolor")
    public static void setColor(View view, String type) {
        Context context = MyApplication.getApplicationCtx();
        String[] statusName = context.getResources().getStringArray(R.array.status_name);
        String[] statusColor = context.getResources().getStringArray(R.array.status_color);
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        view.setBackgroundColor(color);
        if(type != null) {
            for(int i = 0; i < statusName.length; i++) {
                if(type.equalsIgnoreCase(statusName[i])) {
//                    color = ContextCompat.getColor(context,Color.parseColor(statusColor[0]));
                    view.setBackgroundColor(Color.parseColor(statusColor[i]));
                }
            }
        }
    }
    @BindingAdapter("statusdate")
    public static void setDate(View view, OrderDTO dto) {
        String date = "";
        Context context = MyApplication.getApplicationCtx();
        String[] statusName = context.getResources().getStringArray(R.array.status_name);
        if(dto != null && dto.getStatus() != null) {
            if(dto.getStatus().equalsIgnoreCase(statusName[0])) {
                date = dto.getOrderOn();
            } else if(dto.getStatus().equalsIgnoreCase(statusName[1])) {
                date = dto.getPickedOn();
            } else if(dto.getStatus().equalsIgnoreCase(statusName[2])) {
                date = dto.getDeliverOn();
            } else if(dto.getStatus().equalsIgnoreCase(statusName[3])) {
                date = "";
            } else if(dto.getStatus().equalsIgnoreCase(statusName[4])) {
                date = "";
            } else if(dto.getStatus().equalsIgnoreCase(statusName[5])) {
                date = dto.getReturnOn();
            }
        ((TextView) view).setText(dto.getStatus()+" On "+date);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchMatch = true;
                    mListFiltered = mList;
                } else {
                    List<OrderDTO> filteredList = new ArrayList<>();
                    for (OrderDTO orderDTO : mList) {
                        if (orderDTO.getOrderId().toLowerCase().contains(charString.toLowerCase())
                                || orderDTO.getStatus().toLowerCase().contains(charString.toLowerCase())
                                || orderDTO.getSeller().toLowerCase().contains(charString.toLowerCase())
                                || orderDTO.getProduct().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(orderDTO);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        searchMatch = false;
                    } else {
                        searchMatch = true;
                    }
                    mListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListFiltered = (ArrayList<OrderDTO>) results.values;
                if (!searchMatch || mListFiltered.isEmpty()) {
                    viewModel.isRefresh.setValue(false);
                    viewModel.isDataAvailable.setValue(false);
//                    viewModel.manualCheck.setValue(false);
                } else {
                    viewModel.isRefresh.setValue(false);
                    viewModel.isDataAvailable.setValue(true);
//                    viewModel.manualCheck.setValue(true);
                }
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements OnClickHandler {

        private  RowItemOrderListBinding binding;
        private  RowItemAutoCompleteBinding cbinding;

        MyViewHolder(RowItemOrderListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            binding.setHandler(this);
            binding.executePendingBindings();
        }

        MyViewHolder(RowItemAutoCompleteBinding itemBinding) {
            super(itemBinding.getRoot());
            this.cbinding = itemBinding;
            cbinding.executePendingBindings();
        }

        @Override
        public void onClick(Object obj) {
            OrderDTO requestDTO = (OrderDTO) obj;
            Intent intent = new Intent(context, CreateRecordActivity.class);
            intent.putExtra(AppConstants.DATA, requestDTO);
            ((Activity) context).startActivity(intent);
        }
    }
}
