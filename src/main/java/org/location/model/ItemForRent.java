package org.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemForRent {
    private final String id;
    private final String name;
    private final String description;
    private final double pricePerDay;
    private Status status;
}