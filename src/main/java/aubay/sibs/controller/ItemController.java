package aubay.sibs.controller;

import aubay.sibs.model.Item;
import aubay.sibs.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAll(){
        return itemService.getAll();
    }

    @GetMapping("{id}")
    public Item getItemById(@PathVariable Long id){
        return itemService.getById(id);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item){
        return itemService.create(item);
    }

    @PutMapping("{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item){
        return itemService.update(id, item);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.delete(id);
    }
}
