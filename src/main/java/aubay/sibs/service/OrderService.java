package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.Order;
import aubay.sibs.model.StockMovement;
import aubay.sibs.model.dto.OrdersCompletedDTO;
import aubay.sibs.model.dto.StocksToCompleteOrderDTO;
import aubay.sibs.repository.OrderRepository;
import aubay.sibs.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final EmailService emailService;

    private final StockMovementRepository stockMovementRepository;

    public OrderService(OrderRepository orderRepository, EmailService emailService, StockMovementRepository stockMovementRepository) {
        this.orderRepository = orderRepository;
        this.emailService = emailService;
        this.stockMovementRepository = stockMovementRepository;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no order with given id"));
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order create(Order order){
        order.setCreationDate(new Date());
        boolean canBeCompleted = checkIfOrderCanBeCompleted(order.getItem().getId(), order.getQuantity());
        if(canBeCompleted){
            completeOrder(order);
        }
        return orderRepository.save(order);
    }

    public Order update(Long id, Order order){
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no order with given id"));
        order.setId(existingOrder.getId());
        return orderRepository.save(order);
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }

    public boolean checkIfOrderCanBeCompleted(Long itemId, int quantityOrdered){
        int available = stockMovementRepository.getTotalMovementByItemId(itemId);
        int completed = orderRepository.getTotalOrderedByItemId(itemId);
        return  available - completed >= quantityOrdered;
    }

    public void completeOrder(Order order){
        String orderEmail = "Order with item " + order.getItem().getName() + "is completed";
        emailService.sendEmail(orderEmail, order.getUser());
    }

    public StocksToCompleteOrderDTO getStockMovementsToCompleteOrder(Long orderId){
        Order order = getById(orderId);
        List<StockMovement> stockMovements = stockMovementRepository.findAllByOrderByCreationDateAsc();
        List<Order> orders = orderRepository.findAllByOrderByCreationDateAsc();
        Map<Order, List<StockMovement>> orderToStockMovement = new HashMap<>();
        for (Order currentOrder : orders) {
            List<StockMovement> stocksToCompleteOrder = new ArrayList<>();
            Iterator<StockMovement> stockIterator = stockMovements.iterator();
            while (currentOrder.getQuantity() != 0 && stockIterator.hasNext()) {
                StockMovement currentStock = stockIterator.next();
                StockMovement stockMovement = new StockMovement();
                stockMovement.setQuantity(currentStock.getQuantity());
                stockMovement.setId(currentStock.getId());
                stockMovement.setCreationDate(currentStock.getCreationDate());
                stockMovement.setItem(currentStock.getItem());
                stocksToCompleteOrder.add(stockMovement);
                if (currentOrder.getQuantity() < currentStock.getQuantity()) {
                    currentStock.setQuantity(currentStock.getQuantity() - currentOrder.getQuantity());
                    currentOrder.setQuantity(0);
                } else if (currentOrder.getQuantity() > currentStock.getQuantity()) {
                    currentOrder.setQuantity(currentOrder.getQuantity() - currentStock.getQuantity());
                    stockIterator.remove();
                } else {
                    currentOrder.setQuantity(0);
                }
                orderToStockMovement.put(currentOrder, stocksToCompleteOrder);
            }
        }
        StocksToCompleteOrderDTO stocksToCompleteOrderDTO = new StocksToCompleteOrderDTO();
        stocksToCompleteOrderDTO.setStockMovementsToCompleteOrder(orderToStockMovement.get(order));
        return stocksToCompleteOrderDTO;
    }

    public OrdersCompletedDTO getOrdersCompleteness(){
        List<StockMovement> stockMovements = stockMovementRepository.findAllByOrderByCreationDateAsc();
        List<Order> orders = orderRepository.findAllByOrderByCreationDateAsc();
        for (Order currentOrder : orders) {
            Iterator<StockMovement> stockIterator = stockMovements.iterator();
            while (currentOrder.getQuantity() != 0 && stockIterator.hasNext()) {
                StockMovement currentStock = stockIterator.next();
                if (currentOrder.getQuantity() < currentStock.getQuantity()) {
                    currentOrder.setQuantity(0);
                } else if (currentOrder.getQuantity() > currentStock.getQuantity()) {
                    currentOrder.setQuantity(currentOrder.getQuantity() - currentStock.getQuantity());
                } else {
                    currentOrder.setQuantity(0);
                }
            }
        }
        OrdersCompletedDTO ordersCompletedDTO = new OrdersCompletedDTO();
        ordersCompletedDTO.setOrdersWithCompleteness(orders);
        return ordersCompletedDTO;
    }
}
