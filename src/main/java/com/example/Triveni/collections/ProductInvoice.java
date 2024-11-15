package com.example.Triveni.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "information_object")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInvoice implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_invoice_id", nullable = false)
    @JsonIgnore
    private Invoice invoice;

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

    @Column(name = "created_time")
    private Timestamp createdTime;

}
