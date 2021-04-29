package me.erittenhouse.purplecowapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ItemDto {
    private int id;
    @NonNull
    private String name;
}

