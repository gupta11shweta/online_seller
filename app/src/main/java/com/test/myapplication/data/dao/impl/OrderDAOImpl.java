package com.test.myapplication.data.dao.impl;

import com.test.myapplication.data.AppDatabase;
import com.test.myapplication.data.dao.OrderDAO;
import com.test.myapplication.model.OrderDTO;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private AppDatabase db = AppDatabase.getDatabase();

    @Override
    public List<OrderDTO> getAll() {
        return db.OrderDAO().getAll();
    }

    @Override
    public void insert(OrderDTO orderDTO) {
        db.runInTransaction(() -> {
            db.OrderDAO().insert(orderDTO);
        });
    }

    @Override
    public OrderDTO getOrderById(String id) {
        return db.OrderDAO().getOrderById(id);
    }

    @Override
    public List<OrderDTO> getOrderByProduct(String name) {
        return db.OrderDAO().getOrderByProduct(name);
    }

    @Override
    public List<OrderDTO> getOrderBySeller(String name) {
        return db.OrderDAO().getOrderBySeller(name);
    }
}
