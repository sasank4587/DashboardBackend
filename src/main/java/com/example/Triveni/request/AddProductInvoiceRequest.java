package com.example.Triveni.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductInvoiceRequest {
    private List<ProductInvoiceRequest> productInvoiceRequestList;
    private String status;
}
