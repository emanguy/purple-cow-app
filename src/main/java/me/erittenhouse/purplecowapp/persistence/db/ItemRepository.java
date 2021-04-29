package me.erittenhouse.purplecowapp.persistence.db;

import me.erittenhouse.purplecowapp.entity.ItemEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ItemRepository extends CrudRepository<ItemEntity, Integer> {
    @Query("SELECT * FROM items WHERE deleted_at IS NULL")
    List<ItemEntity> all();

    @Modifying
    @Query("INSERT INTO items(name) VALUE (:itemName)")
    void createItem(String itemName);

    @Query("SELECT LAST_INSERT_ID()")
    Integer getInsertedID();

    @Query("SELECT * FROM items WHERE id = :itemID")
    ItemEntity getByID(int itemID);

    @Modifying
    @Query("UPDATE items SET name = :newName WHERE id = :itemID")
    void updateItemByID(int itemID, String newName);

    @Modifying
    @Query("UPDATE items SET deleted_at = NOW() WHERE id = :itemID")
    void deleteItemByID(int itemID);
}
