package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.StockMovement;
import aubay.sibs.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    public StockMovement getById(Long id) {
        return stockMovementRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no stock movement with given id"));
    }

    public List<StockMovement> getAll(){
        return stockMovementRepository.findAll();
    }

    public StockMovement create(StockMovement stockMovement){
        stockMovement.setCreationDate(new Date());
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
}
