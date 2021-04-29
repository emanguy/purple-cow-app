package me.erittenhouse.purplecowapp.service;

import me.erittenhouse.purplecowapp.entity.ItemEntity;
import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ItemService {
    private final List<ItemEntity> inMemoryItems = new ArrayList<>();
    private final Random randomGenerator = new Random();

    /**
     * Fetches a list of all existing items in the system.
     * @return The list of items.
     */
    public List<ItemEntity> listItems() {
        return inMemoryItems;
    }

    /**
     * Creates a new item.
     * @param itemCreateRequest Required data for creating the item
     * @return The ID of the newly created item
     */
    public int createItem(CreateItemDto itemCreateRequest) {
        ItemEntity createdItem = new ItemEntity(randomGenerator.nextInt(), itemCreateRequest.getName(), LocalDate.now(), null, null);
        inMemoryItems.add(createdItem);
        return createdItem.getId();
    }

    /**
     * Fetches an item with the matching ID, or null if it is not found.
     * @param itemID The ID of the item you want to find
     * @return The item if it exists, or null otherwise
     */
    public ItemEntity getItemByID(int itemID) {
        for (ItemEntity item : inMemoryItems) {
            if (item.getId() == itemID) {
                return item;
            }
        }

        return null;
    }

    /**
     * Updates the item with the given ID. Returns false if the item didn't exist.
     * @param itemID The ID of the item you want to update
     * @param itemUpdateRequest The fields you want to update on the item
     */
    public void updateItemByID(int itemID, UpdateItemDto itemUpdateRequest) {
        ItemEntity itemToUpdate = getItemByID(itemID);
        if (itemToUpdate == null) {
            return;
        }

        itemUpdateRequest.apply(itemToUpdate);
    }

    /**
     * Deletes the item with the given ID
     * @param itemID The ID of the item you want to delete
     */
    public void deleteItemByID(int itemID) {
        int itemIndex = -1;
        for (int index = 0; index < inMemoryItems.size(); index++) {
            ItemEntity currentItem = inMemoryItems.get(index);
            if (currentItem.getId() == itemID) {
                itemIndex = index;
                break;
            }
        }

        if (itemIndex != -1) {
            inMemoryItems.remove(itemIndex);
        }
    }
}
