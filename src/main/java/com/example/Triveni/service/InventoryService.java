package com.example.Triveni.service;

import com.example.Triveni.exception.CategoryNotFound;
import com.example.Triveni.request.InventoryRequest;
import com.example.Triveni.response.InventoryCurrentCount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    List<InventoryCurrentCount> getInventoryCount(String category) throws CategoryNotFound;

    void updateInventory(List<InventoryRequest> requestList) throws CategoryNotFound;

    void addNewEntry(InventoryRequest inventoryRequest) throws CategoryNotFound;
}
