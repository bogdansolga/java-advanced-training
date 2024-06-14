package net.safedata.clean.coding.factory;

import net.safedata.clean.coding.OrderProcessingResult;
import net.safedata.clean.coding.OrderStatus;

public class SuccessfulOrderProcessingResult extends AbstractOrderProcessingResult {

    @Override
    public OrderProcessingResult getProcessingResult() {
        return null;
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.FULFILLED;
    }
}
