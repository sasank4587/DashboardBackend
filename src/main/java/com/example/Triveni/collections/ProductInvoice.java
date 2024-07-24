package com.example.Triveni.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "information_object")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_size")
    private String productSize;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "invoice_status")
    private String invoiceStatus;

}
