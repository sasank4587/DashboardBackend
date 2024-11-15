package com.example.Triveni.respositories;

import com.example.Triveni.collections.EntriesAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntriesAuditRepository extends JpaRepository<EntriesAudit, String> {
}
