package org.location.service;

import org.location.model.ItemForRent;

import java.util.Optional;

public interface ItemServiceInterface {
    void loadSampleItems();
    void listItems();
    Optional<ItemForRent> findById(String id);
}
