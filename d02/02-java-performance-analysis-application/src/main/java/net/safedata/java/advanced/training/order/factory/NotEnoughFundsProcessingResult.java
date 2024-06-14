package net.safedata.java.advanced.training.order.factory;

import net.safedata.java.advanced.training.order.OrderProcessingResult;
import net.safedata.java.advanced.training.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class NotEnoughFundsProcessingResult extends AbstractOrderProcessingResult {

    @Override
    public OrderProcessingResult getProcessingResult() {
        // TODO add magic here
        return new OrderProcessingResult();
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.NOT_ENOUGH_FUNDS;
    }
}
