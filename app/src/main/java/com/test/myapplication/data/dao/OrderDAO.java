package com.test.myapplication.data.dao;


import com.test.myapplication.model.OrderDTO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM `order` Order By orderOn ASC")
    List<OrderDTO> getAll();

    @Insert(onConflict = REPLACE)
    void insert(OrderDTO orderDTO);

    @Query("SELECT * FROM `order` Where orderId = :id")
    OrderDTO getOrderById(String id);

    @Query("SELECT * FROM `order` WHERE product like :name")
    List<OrderDTO> getOrderByProduct(String name);

    @Query("SELECT * FROM `order` WHERE seller like :name")
    List<OrderDTO> getOrderBySeller(String name);
}
