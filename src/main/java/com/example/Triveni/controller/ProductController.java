package com.example.Triveni.controller;

import com.example.Triveni.request.AddProductInvoiceRequest;
import com.example.Triveni.response.ErrorResponse;
import com.example.Triveni.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/all")
    private ResponseEntity<?> getAllProducts(){
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/expiry")
    private ResponseEntity<?> getExpiryProducts(){
        try {
            return new ResponseEntity<>(productService.getNearExpiryProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/product/{invoiceId}")
    private ResponseEntity<?> getProductDetailsByInvoiceId(@PathVariable(value = "invoiceId") String invoiceId){
        try {
            return new ResponseEntity<>(productService.getProductsByInvoiceId(invoiceId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/open/invoices")
    private ResponseEntity<?> getOpenInvoices(){
        try {
            return new ResponseEntity<>(productService.getOpenInvoiceIds(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/products")
    private ResponseEntity<?> addProductInvoices(@RequestBody AddProductInvoiceRequest addProductInvoiceRequest){
        try {
            productService.addProductInvoices(addProductInvoiceRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(path = "/dashboard")
    private ResponseEntity<?> getExpiryDashBoard(){
        try {
            return new ResponseEntity<>(productService.getExpiryDashBoard(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
