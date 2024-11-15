package com.example.Triveni.service;

import com.example.Triveni.collections.Invoice;
import com.example.Triveni.collections.ProductInvoice;
import com.example.Triveni.exception.InvoiceDoesNotExist;
import com.example.Triveni.request.AddProductInvoiceRequest;
import com.example.Triveni.request.ProductInvoiceRequest;
import com.example.Triveni.response.DashBoardResponse;
import com.example.Triveni.response.InvoiceResponse;
import com.example.Triveni.response.ProductInvoiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductInvoiceResponse> getAllProducts();

    Page<ProductInvoiceResponse> getFilteredProducts(String invoiceId, Integer pageNumber, Integer pageSize)
            throws InvoiceDoesNotExist;

    List<ProductInvoiceResponse> getNearExpiryProducts();

    List<ProductInvoiceResponse> getProductsByInvoiceId(String invoiceId) throws InvoiceDoesNotExist;

    List<InvoiceResponse> getOpenInvoiceIds();

    void addProductInvoices(ProductInvoiceRequest productInvoiceRequest);

    DashBoardResponse getExpiryDashBoard();

    Invoice closeInvoice(String invoiceId) throws InvoiceDoesNotExist;
}
