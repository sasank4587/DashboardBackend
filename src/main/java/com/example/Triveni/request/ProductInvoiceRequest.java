package com.example.Triveni.request;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInvoiceRequest {
    private String invoiceId;
    private Date invoiceDate;
    private String vendorName;
    private String brandName;
    private String productName;
    private String productSize;
    private Integer productQuantity;
    private Date expirationDate;
    private String status;
}
