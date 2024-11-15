package com.example.Triveni.impl;

import com.example.Triveni.collections.Entries;
import com.example.Triveni.collections.EntriesAudit;
import com.example.Triveni.collections.EntriesCurrent;
import com.example.Triveni.collections.enums.Category;
import com.example.Triveni.collections.enums.Update;
import com.example.Triveni.exception.CategoryNotFound;
import com.example.Triveni.request.InventoryRequest;
import com.example.Triveni.response.InventoryCurrentCount;
import com.example.Triveni.respositories.EntriesAuditRepository;
import com.example.Triveni.respositories.EntriesCurrentRepository;
import com.example.Triveni.respositories.EntriesRepository;
import com.example.Triveni.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private EntriesRepository entriesRepository;

    @Autowired
    private EntriesCurrentRepository entriesCurrentRepository;

    @Autowired
    private EntriesAuditRepository entriesAuditRepository;


    @Override
    public List<InventoryCurrentCount> getInventoryCount(String category) throws CategoryNotFound {
        if(!StringUtils.isEmpty(category)){
            Category categoryType = Category.fromString(category);
            List<EntriesCurrent> entriesCurrentRepositoryList =
                    entriesCurrentRepository.findByCategory(categoryType);
            return entriesCurrentRepositoryList.parallelStream()
                    .map(this::convertInventoryCurrentCount)
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void updateInventory(List<InventoryRequest> requestList) throws CategoryNotFound {
        AtomicReference<CategoryNotFound> categoryNotFoundException = new AtomicReference<>();

        requestList.parallelStream().forEach(inventoryRequest -> {
            try {
                updateInventoryObject(inventoryRequest);
            } catch (CategoryNotFound e) {
                // Capture the exception in the AtomicReference
                categoryNotFoundException.set(e);
            } catch (Exception e) {
                // Log any other unexpected errors
                System.err.println("Unexpected error occurred while updating inventory: " + e.getMessage());
            }
        });

        // After processing, check if a CategoryNotFound exception was captured
        if (categoryNotFoundException.get() != null) {
            throw categoryNotFoundException.get();
        }
    }


    private void updateInventoryObject(InventoryRequest inventoryRequest) throws CategoryNotFound {
        if(!StringUtils.isEmpty(inventoryRequest.getCategory())
                && !StringUtils.isEmpty(inventoryRequest.getEntry()) &&
                inventoryRequest.getCount() != null){
            Category category = Category.fromString(inventoryRequest.getCategory());

            Entries entry = entriesRepository.findByCategoryAndValue(category, inventoryRequest.getEntry());
            if(Objects.nonNull(entry)){
                EntriesCurrent entriesCurrent = entriesCurrentRepository.findByEntry(entry);

                if(!Objects.equals(entriesCurrent.getValue(), inventoryRequest.getCount())){
                    EntriesAudit entriesAudit = new EntriesAudit();
                    entriesAudit.setEntry(entry);
                    entriesAudit.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                    if(inventoryRequest.getCreatedDate() != null){
                        entriesAudit.setCreatedDate(inventoryRequest.getCreatedDate());
                    } else {
                        entriesAudit.setCreatedDate(new Date(System.currentTimeMillis()));
                    }
                    int diff = inventoryRequest.getCount() - entriesCurrent.getValue();
                    if(diff > 0){
                        entriesAudit.setUpdate(Update.INCREMENT);
                        entriesAudit.setValue(diff);
                    } else{
                        entriesAudit.setUpdate(Update.DECREMENT);
                        entriesAudit.setValue(-1 * diff);
                    }
                    entriesAuditRepository.save(entriesAudit);
                    entriesCurrent.setValue(inventoryRequest.getCount());
                    entriesCurrentRepository.save(entriesCurrent);
                }

            }

        }
    }

    @Override
    public void addNewEntry(InventoryRequest inventoryRequest) throws CategoryNotFound {
        if(!StringUtils.isEmpty(inventoryRequest.getCategory())){

            Category category = Category.fromString(inventoryRequest.getCategory());

            Entries entries = new Entries();
            entries.setCategory(category);
            entries.setValue(inventoryRequest.getEntry());
            entries.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            Entries savedEntry = entriesRepository.saveAndFlush(entries);

            EntriesCurrent entriesCurrent = new EntriesCurrent();
            entriesCurrent.setEntry(savedEntry);
            entriesCurrent.setValue(inventoryRequest.getCount());
            entriesCurrent.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            entriesCurrentRepository.save(entriesCurrent);
        }
    }

    private InventoryCurrentCount convertInventoryCurrentCount(EntriesCurrent entriesCurrent){
        return InventoryCurrentCount.builder()
                .entry(entriesCurrent.getEntry().getValue())
                .count(entriesCurrent.getValue())
                .build();
    }
}
