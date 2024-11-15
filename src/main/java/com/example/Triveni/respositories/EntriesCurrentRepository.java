package com.example.Triveni.respositories;

import com.example.Triveni.collections.Entries;
import com.example.Triveni.collections.EntriesCurrent;
import com.example.Triveni.collections.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntriesCurrentRepository extends JpaRepository<EntriesCurrent, String> {

    @Query("select ec from EntriesCurrent ec where ec.entry.category = :category")
    List<EntriesCurrent> findByCategory(Category category);

    EntriesCurrent findByEntry(Entries entry);
}
