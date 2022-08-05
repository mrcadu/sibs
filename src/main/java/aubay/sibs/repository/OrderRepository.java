package aubay.sibs.repository;

import aubay.sibs.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.quantity) FROM Order o where o.item.id = : id")
    int getTotalOrderedByItemId(Long id);
    List<Order> findAllByOrderByCreationDateAsc();
}
