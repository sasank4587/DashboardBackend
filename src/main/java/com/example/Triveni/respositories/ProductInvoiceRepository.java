package com.example.Triveni.respositories;

import com.example.Triveni.collections.Invoice;
import com.example.Triveni.collections.ProductInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductInvoiceRepository extends JpaRepository<ProductInvoice, String> {

//    @Query("select P from ProductInvoice p where p.expirationDate >= :startDate and p.expirationDate <= :enddate")
    List<ProductInvoice> findByExpirationDateBetween(
            Date startDate, Date endDate);

    List<ProductInvoice> findByInvoice(Invoice invoice);

    Page<ProductInvoice> findByInvoice(Invoice invoice, Pageable pageable);
}
