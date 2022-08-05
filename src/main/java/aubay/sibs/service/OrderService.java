package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.Order;
import aubay.sibs.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no order with given id"));
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order create(Order order){
        order.setCreationDate(new Date());
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
}
