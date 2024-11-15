package com.example.Triveni.collections;

import com.example.Triveni.collections.enums.Category;
import com.example.Triveni.collections.enums.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "entries_audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntriesAudit {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    @JsonIgnore
    private Entries entry;

    @Enumerated(EnumType.STRING)
    @Column(name = "update")
    private Update update;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "value")
    private Integer value;

    @Column(name = "created_time")
    private Timestamp createdTime;
}
