package com.example.Triveni.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryCurrentCount {
    private String entry;
    private Integer count;
}
