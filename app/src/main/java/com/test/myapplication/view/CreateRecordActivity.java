package com.test.myapplication.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.test.myapplication.MyApplication;
import com.test.myapplication.R;
import com.test.myapplication.constants.AppConstants;
import com.test.myapplication.constants.DBConstants;
import com.test.myapplication.constants.EnumConstants;
import com.test.myapplication.data.dao.impl.OrderDAOImpl;
import com.test.myapplication.databinding.ActivityCreateBinding;
import com.test.myapplication.model.MessageRequestDTO;
import com.test.myapplication.model.OrderDTO;
import com.test.myapplication.viewModel.CreateRecordViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

public class CreateRecordActivity extends BaseActivity {

    private CreateRecordViewModel viewModel;
    private ActivityCreateBinding dataBinding;
    private OrderDTO orderDTO;
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {"Please search..."};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }
    private void initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        initViewModel();
        dataBinding.setViewModel(viewModel);
        dataBinding.setLifecycleOwner(this);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        dataBinding.etProductName.setAdapter(myAdapter);
        dataBinding.etProductName.setOnItemClickListener(onItemClickListener);
        dataBinding.etProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence userInput, int start, int before, int count) {
                // query the database based on the user input
                item = getProductFromDb(userInput.toString());

                // update the adapater
                myAdapter.notifyDataSetChanged();
                myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, item);
                dataBinding.etProductName.setAdapter(myAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String[] getProductFromDb(String s) {
        List<OrderDTO> products = new OrderDAOImpl().getOrderByProduct(s);
        int rowCount = products.size();
        String[] item = new String[rowCount];
        int x = 0;
        for (OrderDTO record : products) {
            item[x] = record.getProduct();
            x++;
        }
        return item;
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(CreateRecordActivity.this,
                            "Clicked item from auto completion list "
                                    + adapterView.getItemAtPosition(i)
                            , Toast.LENGTH_SHORT).show();
                }
            };

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this).get(CreateRecordViewModel.class);
        viewModel = new CreateRecordViewModel(MyApplication.getApplication(),this);

        viewModel.getListClick().observe(this, aBoolean -> {
            if(aBoolean != null && aBoolean){
                launchListActivity();
            }
        });

        viewModel.getBackUpClick().observe(this, aBoolean -> {
            if(aBoolean != null && aBoolean){
                requestPermission();
            }
        });

        orderDTO = (OrderDTO) getIntent().getSerializableExtra(AppConstants.DATA);
        if (orderDTO != null) {
            viewModel.setData(orderDTO);
        }
    }

    private void launchListActivity() {
        Intent intent = new Intent(this, RecordListActivity.class);
        startActivity(intent);
    }

    public void createBackup() {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PE");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
           String DirectoryName = folder.getPath() + File.separator +  new SimpleDateFormat("MMM-yy").format(new Date());
            folder = new File(DirectoryName);
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(this, DBConstants.DB_NAME.getValue(), DirectoryName);
                sqliteToExcel.exportSingleTable ("order",DBConstants.DB_NAME.getValue() + ".xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("Order", "onError: " +e.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                exportDB(DirectoryName);
            }
        } else {
            Toast.makeText(this, "Not create folder", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportDB(String filename) {
        try {
            File dbFile = new File(this.getDatabasePath(DBConstants.DB_NAME.getValue()).getAbsolutePath());
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = filename + File.separator +
                    DBConstants.DB_NAME.getValue() + ".xls";

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();


        } catch (IOException e) {
            Log.e("dbBackup:", e.getMessage());
        }
    }

    private void requestPermission() {
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 8);
        }else{
            createBackup();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 8) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        for (int i = 0, len = permissions.length; i < len; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                String permission = permissions[i];
                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    permissionDialog(this);
                }
            }
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createBackup();
        }
    }

    private static void permissionDialog(@NonNull Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.permission_required);
        builder.setCancelable(false);
        builder.setMessage(R.string.storage_permission);
        builder.setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + mContext.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
        builder.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onClickEvent(MessageRequestDTO messageRequestDTO) {
        if(EnumConstants.dialogTagEnum.STATUS_TYPE.value().equalsIgnoreCase(messageRequestDTO.getName())) {
            viewModel.onStatusSelected((String)messageRequestDTO.getData());
        }
    }
}
