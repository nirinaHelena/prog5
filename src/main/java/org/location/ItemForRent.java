package org.location;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class ItemForRent {
    private final String id;
    private final String name;
    private final String description;
    private final double pricePerDay;
    private final Status status;
}
