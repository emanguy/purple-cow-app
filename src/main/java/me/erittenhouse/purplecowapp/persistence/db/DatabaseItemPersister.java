package me.erittenhouse.purplecowapp.persistence.db;

import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import me.erittenhouse.purplecowapp.entity.ItemEntity;
import me.erittenhouse.purplecowapp.persistence.ItemPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("db")
public class DatabaseItemPersister implements ItemPersister {
    private final ItemRepository itemRepository;

    @Autowired
    public DatabaseItemPersister(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemEntity> getItems() {
        return itemRepository.all();
    }

    @Override
    public int createItem(CreateItemDto itemCreateRequest) {
        itemRepository.createItem(itemCreateRequest.getName());
        return itemRepository.getInsertedID();
    }

    @Override
    public ItemEntity getItemByID(int id) {
        return itemRepository.getByID(id);
    }

    @Override
    public void updateItemByID(int id, UpdateItemDto itemUpdateRequest) {
        itemRepository.updateItemByID(id, itemUpdateRequest.getName());
    }

    @Override
    public void deleteItemByID(int id) {
        itemRepository.deleteItemByID(id);
    }
}
