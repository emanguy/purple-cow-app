package me.erittenhouse.purplecowapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import me.erittenhouse.purplecowapp.dto.ItemDto;
import org.joda.time.LocalDate;


@Data
@AllArgsConstructor
public class ItemEntity {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public ItemDto toDto() {
        return new ItemDto(id, name);
    }
}
