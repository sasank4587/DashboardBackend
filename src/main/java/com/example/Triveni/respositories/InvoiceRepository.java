package com.example.Triveni.respositories;

import com.example.Triveni.collections.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    Optional<Invoice> findByInvoiceId(String invoiceId);

    List<Invoice> findByInvoiceStatus(String status);
}
