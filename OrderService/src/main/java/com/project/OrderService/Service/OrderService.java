package com.project.OrderService.Service;

import com.project.OrderService.Models.OrderRequest;
import com.project.OrderService.Models.OrderResponse;
import com.project.OrderService.Models.ResponseOrderDetails;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);

    ResponseOrderDetails showOrder(Long orderId);
}
