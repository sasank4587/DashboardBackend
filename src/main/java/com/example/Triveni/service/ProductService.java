package com.example.Triveni.service;

import com.example.Triveni.request.AddProductInvoiceRequest;
import com.example.Triveni.request.ProductInvoiceRequest;
import com.example.Triveni.response.DashBoardResponse;
import com.example.Triveni.response.InvoiceResponse;
import com.example.Triveni.response.ProductInvoiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductInvoiceResponse> getAllProducts();

    List<ProductInvoiceResponse> getNearExpiryProducts();

    List<ProductInvoiceResponse> getProductsByInvoiceId(String invoiceId);

    List<InvoiceResponse> getOpenInvoiceIds();

    void addProductInvoices(AddProductInvoiceRequest addProductInvoiceRequest);

    DashBoardResponse getExpiryDashBoard();
}
