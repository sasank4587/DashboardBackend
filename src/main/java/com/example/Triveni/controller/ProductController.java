package com.example.Triveni.controller;

import com.example.Triveni.exception.InvoiceDoesNotExist;
import com.example.Triveni.request.AddProductInvoiceRequest;
import com.example.Triveni.request.ProductInvoiceRequest;
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

    @GetMapping(path = "/filter")
    private ResponseEntity<?> getFilteredProducts(
            @RequestParam(required = false) String invoiceId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize){
        try {
            return new ResponseEntity<>(productService.getFilteredProducts(invoiceId, pageNo, pageSize), HttpStatus.OK);
        } catch (InvoiceDoesNotExist e) {
            return new ResponseEntity<>(new ErrorResponse("This Invoice does not exist."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping(path = "/{invoiceId}")
    private ResponseEntity<?> getProductDetailsByInvoiceId(@PathVariable(value = "invoiceId") String invoiceId){
        try {
            return new ResponseEntity<>(productService.getProductsByInvoiceId(invoiceId), HttpStatus.OK);
        } catch (InvoiceDoesNotExist e) {
            return new ResponseEntity<>(new ErrorResponse("This Invoice does not exist."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping("/add")
    private ResponseEntity<?> addProductInvoices(@RequestBody ProductInvoiceRequest productInvoiceRequest){
        try {
            productService.addProductInvoices(productInvoiceRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            return new ResponseEntity<>(productService.
                    getProductsByInvoiceId(productInvoiceRequest.getInvoiceId()), HttpStatus.OK);
        } catch (InvoiceDoesNotExist e) {
            return new ResponseEntity<>(new ErrorResponse("This Invoice does not exist."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/status/{invoiceId}")
    private ResponseEntity<?> CloseInvoice(@PathVariable(value = "invoiceId") String invoiceId){
        try {
            return new ResponseEntity<>(productService.closeInvoice(invoiceId), HttpStatus.OK);
        } catch (InvoiceDoesNotExist e) {
            return new ResponseEntity<>(new ErrorResponse("This Invoice does not exist."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
