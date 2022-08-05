package aubay.sibs.controller;

import aubay.sibs.model.StockMovement;
import aubay.sibs.service.StockMovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stockMovement")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping("{id}")
    public StockMovement getById(@PathVariable Long id){
        return stockMovementService.getById(id);
    }

    @GetMapping
    public List<StockMovement> getAll(){
        return stockMovementService.getAll();
    }

    @PostMapping
    public StockMovement create(@RequestBody StockMovement stockMovement){
        return stockMovementService.create(stockMovement);
    }

    @PutMapping("{id}")
    public StockMovement update(@PathVariable Long id, @RequestBody StockMovement stockMovement){
        return stockMovementService.update(id, stockMovement);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        stockMovementService.delete(id);
    }
}
