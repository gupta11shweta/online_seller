package com.test.myapplication.data;


import com.test.myapplication.MyApplication;
import com.test.myapplication.constants.DBConstants;
import com.test.myapplication.data.dao.OrderDAO;
import com.test.myapplication.model.OrderDTO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OrderDTO.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract OrderDAO OrderDAO();

    public static AppDatabase getDatabase() {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(MyApplication.getApplicationCtx(), AppDatabase.class, DBConstants.DB_NAME.getValue())
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}