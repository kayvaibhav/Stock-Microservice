package com.project.OrderService.Service;

import com.project.OrderService.Entities.Order;
import com.project.OrderService.Errors.CustomError;
import com.project.OrderService.External.Client.StockService;
import com.project.OrderService.External.Objects.Stock;
import com.project.OrderService.Models.Details.OrderDetails;
import com.project.OrderService.Models.Details.StockDetails;
import com.project.OrderService.Models.OrderRequest;
import com.project.OrderService.Models.OrderResponse;
import com.project.OrderService.Models.ResponseOrderDetails;
import com.project.OrderService.Repositories.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceIMPL implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockService stockService;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        log.info("CHECKING FOR STOCK...");
        stockService.reduce(orderRequest.getStockId(), orderRequest.getOrderQuantity());
        log.info("CREATING ORDER...");
        Order order = Order.builder()
                .stockId(orderRequest.getStockId())
                .orderQuantity(orderRequest.getOrderQuantity())
                .orderAmount(orderRequest.getOrderAmount())
                .orderTime(Instant.now())
                .orderStatus("CREATED")
                .build();
        orderRepository.save(order);
        return OrderResponse.builder()
                .message("YOUR ORDER HAS BEEN PLACED!")
                .orderId(order.getOrderId())
                .orderTime(Instant.now())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    @Override
    public ResponseOrderDetails showOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new CustomError("The OrderId Doesnt Exist","Try A Different OrderId"));
        Stock stock = restTemplate.getForObject("http://STOCK-SERVICE/stocks/show/"+order.getStockId(),Stock.class);

        StockDetails stockDetails = StockDetails.builder()
                .stockMessage("Here's Stock Information :")
                .stockId(stock.getStockId())
                .stockName(stock.getStockName())
                .stockPrice(stock.getStockPrice())
                .stockQuantity(stock.getStockQuantity())
                .stockTime(stock.getStockTime())
                .build();
        OrderDetails orderDetails = OrderDetails.builder()
                .orderMessage("Here's Your Order Information :")
                .orderId(order.getOrderId())
                .orderTime(order.getOrderTime())
                .orderStatus(order.getOrderStatus())
                .build();
        return ResponseOrderDetails.builder()
                .message("DETAILED ORDER INFORMATION:")
                .orderDetails(orderDetails)
                .stockDetails(stockDetails)
                .build();
    }
}
