package net.safedata.clean.coding;

import net.safedata.clean.coding.factory.AbstractOrderProcessingResult;

public class OrderService {

    public OrderProcessingResult process(Order order) {
        switch (order.getStatus()) {
            case FULFILLED: return processPaymentForOrder(order);
            case NOT_ENOUGH_FUNDS: return displayNotEnoughFunds(order);
            default: throw new IllegalArgumentException("Unknown status");
        }
    }

    public OrderProcessingResult processWithAbstractFactory(Order order) {
        AbstractOrderProcessingResult processingResult = AbstractOrderProcessingResult.createByStatus(order.getStatus());
        return processingResult.getProcessingResult();
    }

    private OrderProcessingResult displayNotEnoughFunds(Order order) {
        return null;
    }

    private OrderProcessingResult processPaymentForOrder(Order order) {
        return null;
    }
}
