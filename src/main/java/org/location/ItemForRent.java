package org.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemForRent {
    private final String id;
    private final String name;
    private final String description;
    private final double pricePerDay;
    private final Status status;
}
