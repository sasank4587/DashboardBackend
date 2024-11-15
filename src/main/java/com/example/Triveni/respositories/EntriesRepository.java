package com.example.Triveni.respositories;

import com.example.Triveni.collections.Entries;
import com.example.Triveni.collections.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntriesRepository extends JpaRepository<Entries, String> {

    Entries findByCategoryAndValue(Category category, String value);
}
