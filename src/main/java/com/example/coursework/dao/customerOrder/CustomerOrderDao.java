package com.example.coursework.dao.customerOrder;

import com.example.coursework.dto.Order;

import java.util.List;

public interface CustomerOrderDao {
    List<Order> getOrderDetailsByCustomerId(int customerId);
    List<Order> getAllOrderDetails();
}
