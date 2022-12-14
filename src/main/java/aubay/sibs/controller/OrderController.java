package aubay.sibs.controller;

import aubay.sibs.model.Order;
import aubay.sibs.model.StockMovement;
import aubay.sibs.model.dto.OrdersCompletedDTO;
import aubay.sibs.model.dto.StocksToCompleteOrderDTO;
import aubay.sibs.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public Order getById(@PathVariable Long id){
        return orderService.getById(id);
    }

    @GetMapping
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @PostMapping
    public Order create(@RequestBody Order order){
        return orderService.create(order);
    }

    @PutMapping("{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order){
        return orderService.update(id, order);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        orderService.delete(id);
    }

    @GetMapping("/stocksToComplete/{id}")
    public StocksToCompleteOrderDTO getStocksToCompleteOrder(@PathVariable Long id){
        return orderService.getStockMovementsToCompleteOrder(id);
    }

    @GetMapping("/completeness")
    public OrdersCompletedDTO getOrdersCompleteness(){
        return orderService.getOrdersCompleteness();
    }
}
