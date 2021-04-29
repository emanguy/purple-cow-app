package me.erittenhouse.purplecowapp.persistence.mem;

import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import me.erittenhouse.purplecowapp.entity.ItemEntity;
import me.erittenhouse.purplecowapp.persistence.ItemPersister;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Qualifier("mem")
public class InMemoryItemPersister implements ItemPersister {
    private int lastID = 0;
    private final List<ItemEntity> inMemoryItems = new ArrayList<>();
    /**
     * The R/W lock here is used to prevent concurrent access issues with the items in the list b/c multiple request threads
     * could try to access it concurrently.
     */
    private final ReentrantReadWriteLock itemAccessLock = new ReentrantReadWriteLock();

    @Override
    public List<ItemEntity> getItems() {
        ReentrantReadWriteLock.ReadLock lock = itemAccessLock.readLock();
        lock.lock();
        try {
            return new ArrayList<>(inMemoryItems);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int createItem(CreateItemDto itemCreateRequest) {
        ReentrantReadWriteLock.WriteLock lock = itemAccessLock.writeLock();
        lock.lock();
        try {
            lastID++;
            ItemEntity createdItem = new ItemEntity(lastID, itemCreateRequest.getName(), LocalDate.now(), null, null);
            inMemoryItems.add(createdItem);
            return createdItem.getId();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ItemEntity getItemByID(int id) {
        ReentrantReadWriteLock.ReadLock lock = itemAccessLock.readLock();
        lock.lock();
        try {
            for (ItemEntity item : inMemoryItems) {
                if (item.getId() == id) {
                    return item;
                }
            }

            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateItemByID(int id, UpdateItemDto itemUpdateRequest) {
        ReentrantReadWriteLock.WriteLock lock = itemAccessLock.writeLock();
        lock.lock();
        try {
            ItemEntity itemToUpdate = getItemByID(id);
            if (itemToUpdate == null) {
                return;
            }

            itemUpdateRequest.apply(itemToUpdate);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteItemByID(int id) {
        ReentrantReadWriteLock.WriteLock lock = itemAccessLock.writeLock();
        lock.lock();
        try {
            int itemIndex = -1;
            for (int index = 0; index < inMemoryItems.size(); index++) {
                ItemEntity currentItem = inMemoryItems.get(index);
                if (currentItem.getId() == id) {
                    itemIndex = index;
                    break;
                }
            }

            if (itemIndex != -1) {
                inMemoryItems.remove(itemIndex);
            }
        } finally {
            lock.unlock();
        }
    }
}
