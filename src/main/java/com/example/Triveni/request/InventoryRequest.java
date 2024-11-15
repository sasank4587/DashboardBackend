package com.example.Triveni.request;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {
    private String category;
    private String entry;
    private Integer count;
    private Date createdDate;
}
