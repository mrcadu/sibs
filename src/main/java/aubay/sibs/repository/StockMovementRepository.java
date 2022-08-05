package aubay.sibs.repository;

import aubay.sibs.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm where sm.item.id = :id")
    int getTotalMovementByItemId(Long id);
}
