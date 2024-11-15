package com.example.Triveni.controller;

import com.example.Triveni.request.InventoryRequest;
import com.example.Triveni.request.UpdateInventoryRequest;
import com.example.Triveni.response.ErrorResponse;
import com.example.Triveni.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/count/{category}")
    private ResponseEntity<?> getAllProducts(@PathVariable(value = "category") String category){
        try {
            return new ResponseEntity<>(inventoryService.getInventoryCount(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    private ResponseEntity<?> addNewCategory(@RequestBody InventoryRequest inventoryRequest){
        try {
            inventoryService.addNewEntry(inventoryRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<?> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest){
        try {
            inventoryService.updateInventory(updateInventoryRequest.getRequestList());
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
