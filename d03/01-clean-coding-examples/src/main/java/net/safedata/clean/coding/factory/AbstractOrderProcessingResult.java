package net.safedata.clean.coding.factory;

import net.safedata.clean.coding.OrderProcessingResult;
import net.safedata.clean.coding.OrderStatus;

public abstract class AbstractOrderProcessingResult {

    public abstract OrderProcessingResult getProcessingResult();

    public abstract OrderStatus getStatus();

    public static AbstractOrderProcessingResult createByStatus(OrderStatus orderStatus) {
        //TODO find a cleaner way to create the dynamic instances
        return switch (orderStatus) {
            case FULFILLED -> new SuccessfulOrderProcessingResult();
            case NOT_ENOUGH_FUNDS -> new NotEnoughFundsProcessingResult();
        };
    }
}
