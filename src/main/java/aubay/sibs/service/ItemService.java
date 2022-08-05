package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.Item;
import aubay.sibs.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public Item getById(Long id){
        return itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no item with given id"));
    }

    public Item create(Item item){
        return itemRepository.save(item);
    }

    public Item update(Long id, Item item){
        Item existingItem = itemRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("There is no item with given id"));
        item.setId(existingItem.getId());
        return itemRepository.save(item);
    }

    public void delete(Long id){
        itemRepository.deleteById(id);
    }
}
