package com.example.Triveni.collections;

import com.example.Triveni.collections.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "entries_current")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntriesCurrent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    @JsonIgnore
    private Entries entry;

    @Column(name = "value")
    private Integer value;

    @Column(name = "created_time")
    private Timestamp createdTime;
}
