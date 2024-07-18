package com.example.Triveni.respositories;

import com.example.Triveni.collections.ProductInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductInvoiceRepository extends JpaRepository<ProductInvoice, Integer> {

//    @Query("select P from ProductInvoice p where p.expirationDate >= :startDate and p.expirationDate <= :enddate")
    List<ProductInvoice> findByExpirationDateBetween(
            Date startDate, Date endDate);
    List<ProductInvoice> findByInvoiceId(String invoiceId);

    @Query("SELECT DISTINCT p.invoiceId FROM ProductInvoice p WHERE p.invoiceStatus = :status")
    List<String> findDistinctInvoiceIdsByStatus(@Param("status") String status);
}
