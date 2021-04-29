package me.erittenhouse.purplecowapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.erittenhouse.purplecowapp.entity.ItemEntity;
import org.joda.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDto {
    public String name;

    public void apply(ItemEntity item) {
        item.setName(name);
        item.setUpdatedAt(LocalDate.now());
    }
}
