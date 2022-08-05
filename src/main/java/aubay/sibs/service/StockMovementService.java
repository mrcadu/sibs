package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.Order;
import aubay.sibs.model.StockMovement;
import aubay.sibs.repository.OrderRepository;
import aubay.sibs.repository.StockMovementRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;

    private final OrderRepository orderRepository;

    private final OrderService orderService;

    private final Logger logger = LogManager.getLogger(StockMovementService.class);
    public StockMovementService(StockMovementRepository stockMovementRepository, OrderRepository orderRepository, OrderService orderService) {
        this.stockMovementRepository = stockMovementRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    public StockMovement getById(Long id) {
        return stockMovementRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no stock movement with given id"));
    }

    public List<StockMovement> getAll(){
        return stockMovementRepository.findAll();
    }

    public StockMovement create(StockMovement stockMovement){
        stockMovement.setCreationDate(new Date());
        completePendingOrders(stockMovement);
        logger.info("Stock movement created on item: " + stockMovement.getItem().getId() + "with quantity:" + stockMovement.getQuantity());
        return stockMovementRepository.save(stockMovement);
    }

    public StockMovement update(Long id, StockMovement stockMovement){
        StockMovement existingStockMovement = stockMovementRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no stock movement with given id"));
        stockMovement.setId(existingStockMovement.getId());
        return stockMovementRepository.save(stockMovement);
    }

    public void delete(Long id){
        stockMovementRepository.deleteById(id);
    }

    public void completePendingOrders(StockMovement stockMovement){
        List<Order> orders = orderRepository.findAllByOrderByCreationDateAsc();
        int existingStockMovement = stockMovementRepository.getTotalMovementByItemId(stockMovement.getItem().getId()) + stockMovement.getQuantity();
        AtomicInteger orderCompletedQuantity = new AtomicInteger();
        orders.forEach((order)->{
            if(order.getQuantity() + orderCompletedQuantity.get() <= existingStockMovement){
                orderService.completeOrder(order);
                orderCompletedQuantity.addAndGet(order.getQuantity());
            }
        });
    }
}
