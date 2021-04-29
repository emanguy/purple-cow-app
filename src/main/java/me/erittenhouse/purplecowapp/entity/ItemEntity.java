package me.erittenhouse.purplecowapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import me.erittenhouse.purplecowapp.dto.ItemDto;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;


@Data
@AllArgsConstructor
public class ItemEntity {
    private int id;
    private String name;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate createdAt;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate updatedAt;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate deletedAt;

    public ItemDto toDto() {
        return new ItemDto(id, name);
    }
}
