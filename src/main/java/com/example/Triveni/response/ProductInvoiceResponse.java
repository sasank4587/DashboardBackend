package com.example.Triveni.response;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInvoiceResponse {
    private String invoiceId;
    private Date invoiceDate;
    private String vendorName;
    private String brandName;
    private String productName;
    private String productSize;
    private Integer productQuantity;
    private Date expirationDate;
}
