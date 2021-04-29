package me.erittenhouse.purplecowapp.controller;

import me.erittenhouse.purplecowapp.entity.ItemEntity;
import me.erittenhouse.purplecowapp.dto.CreateItemDto;
import me.erittenhouse.purplecowapp.dto.ItemCreatedDto;
import me.erittenhouse.purplecowapp.dto.ItemDto;
import me.erittenhouse.purplecowapp.dto.UpdateItemDto;
import me.erittenhouse.purplecowapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDto> listItems() {
        return itemService.listItems().stream().map(ItemEntity::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ItemCreatedDto> createItem(@RequestBody CreateItemDto createBody) {
        int createdID = itemService.createItem(createBody);
        return new ResponseEntity<>(new ItemCreatedDto(createdID), HttpStatus.CREATED);
    }

    @GetMapping("/{itemID}")
    public ResponseEntity<ItemDto> getItemByID(@PathVariable("itemID") int id) {
        ItemEntity foundItem = itemService.getItemByID(id);
        if (foundItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundItem.toDto(), HttpStatus.OK);
    }

    @PatchMapping("/{itemID}")
    public void updateItem(@PathVariable("itemID") int id, @RequestBody UpdateItemDto updateBody) {
        itemService.updateItemByID(id, updateBody);
    }

    @DeleteMapping("/{itemID}")
    public void deleteItem(@PathVariable("itemID") int id) {
        itemService.deleteItemByID(id);
    }
}
