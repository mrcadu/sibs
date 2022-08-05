package aubay.sibs.model.dto;

import aubay.sibs.model.Order;

import java.util.List;

public class OrdersCompletedDTO {
    private List<Order> ordersWithCompleteness;

    public List<Order> getOrdersWithCompleteness() {
        return ordersWithCompleteness;
    }

    public void setOrdersWithCompleteness(List<Order> ordersWithCompleteness) {
        this.ordersWithCompleteness = ordersWithCompleteness;
    }
}
