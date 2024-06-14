package net.safedata.java.advanced.training.order.factory;

import net.safedata.java.advanced.training.order.OrderProcessingResult;
import net.safedata.java.advanced.training.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractOrderProcessingResult {

    public abstract OrderProcessingResult getProcessingResult();

    public abstract OrderStatus getStatus();
}
