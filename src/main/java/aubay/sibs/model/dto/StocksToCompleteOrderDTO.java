package aubay.sibs.model.dto;

import aubay.sibs.model.StockMovement;
import java.util.List;

public class StocksToCompleteOrderDTO {

    private List<StockMovement> stockMovementsToCompleteOrder;

    public List<StockMovement> getStockMovementsToCompleteOrder() {
        return stockMovementsToCompleteOrder;
    }

    public void setStockMovementsToCompleteOrder(List<StockMovement> stockMovementsToCompleteOrder) {
        this.stockMovementsToCompleteOrder = stockMovementsToCompleteOrder;
    }
}
