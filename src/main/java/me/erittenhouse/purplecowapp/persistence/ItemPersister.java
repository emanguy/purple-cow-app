package me.erittenhouse.purplecowapp.persistence;

import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import me.erittenhouse.purplecowapp.entity.ItemEntity;

import java.util.List;

public interface ItemPersister {
    List<ItemEntity> getItems();
    int createItem(CreateItemDto itemCreateRequest);
    ItemEntity getItemByID(int id);
    void updateItemByID(int id, UpdateItemDto itemUpdateRequest);
    void deleteItemByID(int id);
}
