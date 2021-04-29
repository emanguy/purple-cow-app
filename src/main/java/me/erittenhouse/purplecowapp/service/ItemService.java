package me.erittenhouse.purplecowapp.service;

import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import me.erittenhouse.purplecowapp.entity.ItemEntity;
import me.erittenhouse.purplecowapp.persistence.ItemPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemPersister itemPersister;

    /**
     * Constructor for ItemService. You can change the qualifier to change the used persister implementation.
     */
    @Autowired
    public ItemService(@Qualifier("db") ItemPersister itemPersister) {
        this.itemPersister = itemPersister;
    }

    /**
     * Fetches a list of all existing items in the system.
     * @return The list of items.
     */
    public List<ItemEntity> listItems() {
        return itemPersister.getItems();
    }

    /**
     * Creates a new item.
     * @param itemCreateRequest Required data for creating the item
     * @return The ID of the newly created item
     */
    public int createItem(CreateItemDto itemCreateRequest) {
        return itemPersister.createItem(itemCreateRequest);
    }

    /**
     * Fetches an item with the matching ID, or null if it is not found.
     * @param itemID The ID of the item you want to find
     * @return The item if it exists, or null otherwise
     */
    public ItemEntity getItemByID(int itemID) {
        return itemPersister.getItemByID(itemID);
    }

    /**
     * Updates the item with the given ID. Returns false if the item didn't exist.
     * @param itemID The ID of the item you want to update
     * @param itemUpdateRequest The fields you want to update on the item
     */
    public void updateItemByID(int itemID, UpdateItemDto itemUpdateRequest) {
        itemPersister.updateItemByID(itemID, itemUpdateRequest);
    }

    /**
     * Deletes the item with the given ID
     * @param itemID The ID of the item you want to delete
     */
    public void deleteItemByID(int itemID) {
        itemPersister.deleteItemByID(itemID);
    }
}
